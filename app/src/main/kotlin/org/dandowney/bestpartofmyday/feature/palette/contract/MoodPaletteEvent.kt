package org.dandowney.bestpartofmyday.feature.palette.contract

import org.dandowney.bestpartofmyday.base.ViewEvent
import org.dandowney.bestpartofmyday.library.colorpicker.HsvColor

sealed interface MoodPaletteEvent : ViewEvent {

  object CreateNewPalette : MoodPaletteEvent

  data class EditPalette(val moodPaletteState: MoodPaletteState) : MoodPaletteEvent

  data class ActivatePalette(val moodPaletteState: MoodPaletteState) : MoodPaletteEvent

  data class OnColorSelected(val index: Int) : MoodPaletteEvent

  object OnColorAdded : MoodPaletteEvent

  data class OnColorRemoved(val index: Int) : MoodPaletteEvent

  data class OnColorUpdated(val newValue: HsvColor) : MoodPaletteEvent

  data class OnSavePalette(val editState: MoodPaletteScreenState.EditMoodPalette) : MoodPaletteEvent

  data class OnNameUpdated(val name: String) : MoodPaletteEvent
}
