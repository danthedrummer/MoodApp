package org.dandowney.bestpartofmyday.feature.palette.contract

import org.dandowney.bestpartofmyday.base.ViewState
import org.dandowney.bestpartofmyday.domain.models.MoodPalette
import org.dandowney.bestpartofmyday.library.colorpicker.HsvColor

sealed interface MoodPaletteScreenState : ViewState {

  object Loading : MoodPaletteScreenState

  data class ViewAllMoodPalettes(
    val moodPalettes: List<MoodPaletteState>,
    val activeColors: List<ULong>,
  ) : MoodPaletteScreenState

  data class EditMoodPalette(
    val id: Long,
    val name: String,
    val colors: List<HsvColor>,
    val selectedColorIndex: Int,
    val isActive: Boolean,
  ) : MoodPaletteScreenState

  object CreateMoodPalette : MoodPaletteScreenState
}

data class MoodPaletteState(
  val name: String,
  val colors: List<ULong>,
  val isActive: Boolean,
  val domain: MoodPalette,
)
