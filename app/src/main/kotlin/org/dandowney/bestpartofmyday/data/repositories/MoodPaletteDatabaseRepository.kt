package org.dandowney.bestpartofmyday.data.repositories

import dagger.Reusable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.dandowney.bestpartofmyday.data.db.TransactionHandler
import org.dandowney.bestpartofmyday.data.db.crossrefs.MoodPaletteColorCrossRef
import org.dandowney.bestpartofmyday.data.db.dao.ColorDao
import org.dandowney.bestpartofmyday.data.db.dao.MoodPaletteColorsDao
import org.dandowney.bestpartofmyday.data.db.dao.MoodPaletteDao
import org.dandowney.bestpartofmyday.data.db.entities.ColorEntity
import org.dandowney.bestpartofmyday.data.db.entities.MoodPaletteEntity
import org.dandowney.bestpartofmyday.data.mapper.toDomain
import org.dandowney.bestpartofmyday.data.mapper.toEntity
import org.dandowney.bestpartofmyday.design.theme.Cyan300
import org.dandowney.bestpartofmyday.design.theme.DeepOrange300
import org.dandowney.bestpartofmyday.design.theme.Gray200
import org.dandowney.bestpartofmyday.design.theme.Gray800
import org.dandowney.bestpartofmyday.design.theme.Green300
import org.dandowney.bestpartofmyday.design.theme.LightBlue300
import org.dandowney.bestpartofmyday.design.theme.Lime300
import org.dandowney.bestpartofmyday.design.theme.Orange300
import org.dandowney.bestpartofmyday.design.theme.Pink300
import org.dandowney.bestpartofmyday.design.theme.Purple300
import org.dandowney.bestpartofmyday.design.theme.Red300
import org.dandowney.bestpartofmyday.design.theme.Teal300
import org.dandowney.bestpartofmyday.design.theme.Yellow300
import org.dandowney.bestpartofmyday.domain.models.MoodPalette
import org.dandowney.bestpartofmyday.domain.repositories.MoodPaletteRepository
import javax.inject.Inject

@Reusable
internal class MoodPaletteDatabaseRepository @Inject constructor(
  private val transactionHandler: TransactionHandler,
  private val moodPaletteDao: MoodPaletteDao,
  private val colorDao: ColorDao,
  private val moodPaletteColorsDao: MoodPaletteColorsDao,
  private val scope: CoroutineScope,
) : MoodPaletteRepository {

  private var activeMoodPaletteFlow = MutableSharedFlow<MoodPalette?>(
    replay = 1,
    onBufferOverflow = BufferOverflow.DROP_OLDEST,
  )

  init {
    createDefaults()
    collectActivePalette()
  }

  private fun createDefaults() {
    scope.launch(Dispatchers.IO) {
      if (moodPaletteDao.getAll().isEmpty()) {
        createDefaultMoodPalettes()
      }
    }
  }

  private fun collectActivePalette() {
    scope.launch(Dispatchers.IO) {
      moodPaletteDao.flowOfActive().collect { entity ->
        entity?.let {
          val colors = moodPaletteColorsDao.getOrderedColorsWhere(it.moodPaletteId)
            .map { colorEntity -> colorEntity.value.toULong() }
          activeMoodPaletteFlow.emit(it.toDomain(withColors = colors))
        }
      }
    }
  }

  override suspend fun getMoodPalettes(): List<MoodPalette> {
    return moodPaletteDao.getAll().map { paletteEntity ->
      val orderedColors = moodPaletteColorsDao
        .getOrderedColorsWhere(paletteEntity.moodPaletteId)
        .map { it.value.toULong() }
      paletteEntity.toDomain(withColors = orderedColors)
    }
  }

  override suspend fun getMoodPalette(moodPaletteId: Long): MoodPalette? {
    val entity = moodPaletteDao.getWhere(moodPaletteId = moodPaletteId) ?: return null
    val orderedColors = moodPaletteColorsDao.getOrderedColorsWhere(moodPaletteId = moodPaletteId)
    return entity.toDomain(
      withColors = orderedColors.map { it.value.toULong() },
    )
  }

  override suspend fun getActiveMoodPalette(): MoodPalette {
    return activeMoodPaletteFlow.first() ?: error("There should always be an active palette")
  }

  override fun flowOfActiveMoodPalette(): Flow<MoodPalette> = activeMoodPaletteFlow.filterNotNull()

  override suspend fun setActiveMoodPalette(newActivePalette: MoodPalette) {
    if (newActivePalette.id == activeMoodPaletteFlow.first()?.id) return

    val entitiesToUpdate = listOf(
      newActivePalette.copy(isActive = true),
      activeMoodPaletteFlow.first()?.copy(isActive = false),
    ).mapNotNull { it?.toEntity() }
    moodPaletteDao.update(entities = entitiesToUpdate)
  }

  override suspend fun saveMoodPalette(moodPalette: MoodPalette) {
    insertMoodPalette(
      id = moodPalette.id,
      name = moodPalette.name,
      colors = moodPalette.colors,
      isActive = moodPalette.isActive,
    )
  }

  private suspend fun createDefaultMoodPalettes(): MoodPalette {
    transactionHandler {
      insertMoodPalette(
        name = "Sample Palette 1",
        colors = listOf(
          Red300,
          Orange300,
          Yellow300,
          Lime300,
          Green300,
        ).map { it.value },
        isActive = true,
      )
      insertMoodPalette(
        name = "Sample Palette 2",
        colors = listOf(
          Gray800,
          Cyan300,
          Purple300,
          Gray200,
          Pink300,
        ).map { it.value },
      )
      insertMoodPalette(
        name = "Sample Palette 3",
        colors = listOf(
          Yellow300,
          Green300,
          LightBlue300,
        ).map { it.value },
      )
      insertMoodPalette(
        name = "Sample Palette 4",
        colors = listOf(
          Pink300,
          Teal300,
          Purple300,
          Cyan300,
          DeepOrange300,
        ).map { it.value },
      )
    }
    return getActiveMoodPalette()
  }

  private suspend fun insertMoodPalette(
    id: Long = 0L,
    name: String,
    colors: List<ULong>,
    isActive: Boolean = false,
  ) {
    transactionHandler {
      val entityId = moodPaletteDao.insert(
        moodPalette = MoodPaletteEntity(
          moodPaletteId = id,
          name = name,
          isActive = isActive,
        ),
      )

      val newColorValues = colors.map { it.toLong() }
      val existingColorValues = colorDao.getWhere(newColorValues).map { it.value }
      val colorsToCreate = newColorValues
        .filter { it !in existingColorValues }
        .map { ColorEntity(value = it) }
      colorDao.insert(colorsToCreate)

      val existingCrossRefs = moodPaletteColorsDao.getWhere(moodPaletteId = entityId)
      moodPaletteColorsDao.delete(existingCrossRefs)

      val valueIndexedColors = colorDao.getWhere(newColorValues).associateBy { it.value }
      val newCrossRefs = newColorValues.mapIndexedNotNull { index, colorValue ->
        val colorEntity = valueIndexedColors[colorValue] ?: return@mapIndexedNotNull null
        MoodPaletteColorCrossRef(
          moodPaletteId = entityId,
          colorId = colorEntity.colorId,
          position = index,
        )
      }
      moodPaletteColorsDao.insert(newCrossRefs)
    }
  }
}
