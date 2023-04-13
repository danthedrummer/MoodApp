package org.dandowney.bestpartofmyday.feature.palette

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.dandowney.bestpartofmyday.base.BaseViewModel
import org.dandowney.bestpartofmyday.data.usecase.GetActiveMoodPaletteUseCase
import org.dandowney.bestpartofmyday.data.usecase.GetAllMoodPalettesUseCase
import org.dandowney.bestpartofmyday.data.usecase.GetMoodPaletteUseCase
import org.dandowney.bestpartofmyday.data.usecase.SaveMoodPaletteUseCase
import org.dandowney.bestpartofmyday.data.usecase.SetActiveMoodPaletteUseCase
import org.dandowney.bestpartofmyday.domain.models.MoodPalette
import org.dandowney.bestpartofmyday.feature.palette.contract.MoodPaletteEvent
import org.dandowney.bestpartofmyday.feature.palette.contract.MoodPaletteScreenState
import org.dandowney.bestpartofmyday.feature.palette.contract.MoodPaletteSideEffect
import org.dandowney.bestpartofmyday.feature.palette.contract.MoodPaletteState
import org.dandowney.bestpartofmyday.library.colorpicker.HsvColor
import javax.inject.Inject

@HiltViewModel
class MoodPaletteViewModel @Inject constructor(
  private val getAllMoodPalettesUseCase: GetAllMoodPalettesUseCase,
  private val getMoodPaletteUseCase: GetMoodPaletteUseCase,
  private val getActiveMoodPaletteUseCase: GetActiveMoodPaletteUseCase,
  private val setActiveMoodPaletteUseCase: SetActiveMoodPaletteUseCase,
  private val saveMoodPaletteUseCase: SaveMoodPaletteUseCase,
) : BaseViewModel<MoodPaletteEvent, MoodPaletteScreenState, MoodPaletteSideEffect>() {

  init {
    viewModelScope.launch {
      viewAllPalettes()
    }
  }

  override fun createInitialState(): MoodPaletteScreenState = MoodPaletteScreenState.Loading

  override suspend fun handleEvent(event: MoodPaletteEvent) {
    when (event) {
      is MoodPaletteEvent.CreateNewPalette -> handleCreateNewPalette()
      is MoodPaletteEvent.ActivatePalette -> handleActivatePalette(event.moodPaletteState)
      is MoodPaletteEvent.EditPalette -> handleEditPalette(event.moodPaletteState)
      is MoodPaletteEvent.OnColorSelected -> handleColorPositionSelected(event.index)
      is MoodPaletteEvent.OnSavePalette -> handleSavePalette(event.editState)
      is MoodPaletteEvent.OnColorAdded -> handleAddNewColorToPalette()
      is MoodPaletteEvent.OnNameUpdated -> handleNameUpdated(event.name)
      is MoodPaletteEvent.OnColorRemoved -> handleOnColorRemoved(event.index)
      is MoodPaletteEvent.OnColorUpdated -> handleOnColorUpdated(event.newValue)
    }
  }

  private suspend fun viewAllPalettes() {
    val allPalettes = getAllMoodPalettesUseCase()
      .map { domain ->
        MoodPaletteState(
          name = domain.name,
          colors = domain.colors,
          isActive = domain.isActive,
          domain = domain,
        )
      }
    val activePalette = getActiveMoodPaletteUseCase()
    setState {
      MoodPaletteScreenState.ViewAllMoodPalettes(
        moodPalettes = allPalettes,
        activeColors = activePalette.colors,
      )
    }
  }

  private fun handleCreateNewPalette() {
    setState {
      MoodPaletteScreenState.EditMoodPalette(
        id = 0L,
        name = "",
        colors = listOf(HsvColor.from(Color.White)),
        selectedColorIndex = 0,
        isActive = false,
      )
    }
  }

  private suspend fun handleActivatePalette(moodPaletteState: MoodPaletteState) {
    setActiveMoodPaletteUseCase(moodPaletteState.domain)
    setSealedState<MoodPaletteScreenState.ViewAllMoodPalettes> { current ->
      current.copy(
        moodPalettes = current.moodPalettes.map {
          when {
            it == moodPaletteState -> it.copy(
              domain = it.domain.copy(isActive = true),
              isActive = true,
            )
            it.isActive -> it.copy(
              domain = it.domain.copy(isActive = false),
              isActive = false,
            )
            else -> it
          }
        },
        activeColors = moodPaletteState.colors,
      )
    }
  }

  private suspend fun handleEditPalette(moodPaletteState: MoodPaletteState) {
    val moodPalette = getMoodPaletteUseCase(moodPaletteId = moodPaletteState.domain.id)
      ?: error("Requested palette did not exist (id = ${moodPaletteState.domain.id}")
    setState {
      MoodPaletteScreenState.EditMoodPalette(
        id = moodPalette.id,
        name = moodPalette.name,
        colors = moodPalette.colors.map { HsvColor.from(Color(it)) },
        selectedColorIndex = 0,
        isActive = moodPaletteState.domain.isActive,
      )
    }
  }

  private fun handleColorPositionSelected(index: Int) {
    setSealedState<MoodPaletteScreenState.EditMoodPalette> { current ->
      current.copy(
        selectedColorIndex = index,
      )
    }
  }

  private suspend fun handleSavePalette(editState: MoodPaletteScreenState.EditMoodPalette) {
    val updatedPalette = MoodPalette(
      id = editState.id,
      name = editState.name,
      colors = editState.colors.map { it.toColor().value },
      isActive = editState.isActive
    )
    saveMoodPaletteUseCase(updatedPalette)
    viewAllPalettes()
  }

  private fun handleAddNewColorToPalette() {
    setSealedState<MoodPaletteScreenState.EditMoodPalette> { current ->
      current.copy(
        colors = current.colors + HsvColor.from(Color.White),
        selectedColorIndex = current.colors.size,
      )
    }
  }

  private fun handleNameUpdated(name: String) {
    setSealedState<MoodPaletteScreenState.EditMoodPalette> { current ->
      current.copy(
        name = name
      )
    }
  }

  private fun handleOnColorRemoved(index: Int) {
    setSealedState<MoodPaletteScreenState.EditMoodPalette> { current ->
      current.copy(
        colors = current.colors.toMutableList().apply {
          removeAt(index)
        }.toList(),
        selectedColorIndex = if (current.colors.lastIndex == current.selectedColorIndex) {
          current.selectedColorIndex - 1
        } else {
          current.selectedColorIndex
        }
      )
    }
  }

  private fun handleOnColorUpdated(newValue: HsvColor) {
    setSealedState<MoodPaletteScreenState.EditMoodPalette> { current ->
      try {
        current.copy(
          colors = current.colors.toMutableList().apply {
            this[current.selectedColorIndex] = newValue
          }.toList()
        )
      } catch (t: Throwable) {
        println("DanDebug: caught $t")
        current
      }
    }
  }
}
