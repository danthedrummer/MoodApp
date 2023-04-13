package org.dandowney.bestpartofmyday.data.repositories

import org.dandowney.bestpartofmyday.data.db.TransactionHandler
import org.dandowney.bestpartofmyday.data.db.dao.ColorDao
import org.dandowney.bestpartofmyday.data.db.dao.DaySummaryMoodTagDao
import org.dandowney.bestpartofmyday.data.db.dao.MoodTagDao
import org.dandowney.bestpartofmyday.data.db.entities.ColorEntity
import org.dandowney.bestpartofmyday.data.db.entities.MoodTagEntity
import org.dandowney.bestpartofmyday.design.theme.Blue300
import org.dandowney.bestpartofmyday.design.theme.Gray200
import org.dandowney.bestpartofmyday.design.theme.Gray800
import org.dandowney.bestpartofmyday.design.theme.Green300
import org.dandowney.bestpartofmyday.design.theme.LightBlue300
import org.dandowney.bestpartofmyday.design.theme.Lime300
import org.dandowney.bestpartofmyday.design.theme.Orange300
import org.dandowney.bestpartofmyday.design.theme.Pink300
import org.dandowney.bestpartofmyday.design.theme.Red300
import org.dandowney.bestpartofmyday.design.theme.Teal300
import org.dandowney.bestpartofmyday.design.theme.Yellow300
import org.dandowney.bestpartofmyday.domain.models.MoodTag
import org.dandowney.bestpartofmyday.domain.repositories.MoodTagRepository
import javax.inject.Inject

internal class MoodTagDatabaseRepository @Inject constructor(
  private val transactionHandler: TransactionHandler,
  private val moodTagDao: MoodTagDao,
  private val colorDao: ColorDao,
  private val daySummaryMoodTagDao: DaySummaryMoodTagDao,
) : MoodTagRepository {

  override suspend fun getMoodTags(): List<MoodTag> {
    var tags = moodTagDao.getAll()
      .map { it.toDomain() }
    if (tags.isEmpty()) {
      tags = createDefaultMoodTags()
    }
    return tags
  }

  override suspend fun getArchivedMoodTags(): List<MoodTag> {
    return moodTagDao.getArchived()
      .map { it.toDomain() }
  }

  override suspend fun saveMoodTag(moodTag: MoodTag) {
    transactionHandler {
      val color = colorDao.getWhere(value = moodTag.color.toLong())
      val colorId = color?.colorId
        ?: colorDao.insert(
          color = ColorEntity(
            value = moodTag.color.toLong(),
          )
        )

      moodTagDao.insert(
        moodTag = MoodTagEntity(
          moodTagId = moodTag.moodTagId,
          colorId = colorId,
          name = moodTag.name,
        )
      )
    }
  }

  override suspend fun saveMoodTags(moodTags: List<MoodTag>) {
    moodTags.forEach { moodTag -> saveMoodTag(moodTag = moodTag) }
  }

  private suspend fun createDefaultMoodTags(): List<MoodTag> {
    val moodTags = listOf(
      "Happy" to Green300,
      "Angry" to Red300,
      "Sick" to Yellow300,
      "Low" to Blue300,
      "Excited" to Green300,
      "Afraid" to Gray800,
      "Cheerful" to Lime300,
      "Prepared" to Teal300,
      "Calm" to LightBlue300,
      "Embarrassed" to Pink300,
      "Peaceful" to LightBlue300,
      "Grumpy" to Orange300,
      "Euphoric" to Gray200,

    ).map { (name, color) ->
      MoodTag(
        name = name,
        color = color.value,
      )
    }
    saveMoodTags(moodTags)
    return moodTagDao.getAll().map { it.toDomain() }
  }

  override suspend fun archiveMoodTag(moodTag: MoodTag) {
    transactionHandler {
      val entity = moodTagDao.getById(moodTagId = moodTag.moodTagId)
        ?: error("No mood tag exists for ID = ${moodTag.moodTagId}")
      moodTagDao.insert(
        moodTag = entity.copy(
          archived = true,
        ),
      )
    }
  }

  override suspend fun deleteMoodTag(moodTag: MoodTag) {
    transactionHandler {
      moodTagDao.deleteById(moodTagId = moodTag.moodTagId)
      daySummaryMoodTagDao.deleteWhere(moodTagId = moodTag.moodTagId)
    }
  }
}
