package org.dandowney.bestpartofmyday.feature.palette.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import org.dandowney.bestpartofmyday.design.ui.FullScreenLoading
import org.dandowney.bestpartofmyday.feature.palette.MoodPaletteViewModel
import org.dandowney.bestpartofmyday.feature.palette.contract.MoodPaletteEvent
import org.dandowney.bestpartofmyday.feature.palette.contract.MoodPaletteScreenState
import org.dandowney.bestpartofmyday.feature.palette.contract.MoodPaletteState
import org.dandowney.bestpartofmyday.library.colorpicker.HsvColor


@Composable
fun MoodPalettes(
  viewModel: MoodPaletteViewModel = hiltViewModel(),
  onNavigateBack: () -> Unit,
) {
  val state by viewModel.state
  MoodPalettes(
    state = state,
    onEditClicked = { palette -> viewModel.sendEvent(MoodPaletteEvent.EditPalette(palette)) },
    onActivateClicked = { palette -> viewModel.sendEvent(MoodPaletteEvent.ActivatePalette(palette)) },
    onAddPaletteClicked = { viewModel.sendEvent(MoodPaletteEvent.CreateNewPalette) },
    onNameUpdated = { name -> viewModel.sendEvent(MoodPaletteEvent.OnNameUpdated(name)) },
    onColorAdded = { viewModel.sendEvent(MoodPaletteEvent.OnColorAdded) },
    onColorRemoved = { index -> viewModel.sendEvent(MoodPaletteEvent.OnColorRemoved(index)) },
    onColorUpdated = { newValue -> viewModel.sendEvent(MoodPaletteEvent.OnColorUpdated(newValue)) },
    onColorSelected = { index -> viewModel.sendEvent(MoodPaletteEvent.OnColorSelected(index)) },
    onSaveClicked = { editState -> viewModel.sendEvent(MoodPaletteEvent.OnSavePalette(editState)) },
    onNavigateBack = onNavigateBack,
  )
}

@Composable
fun MoodPalettes(
  state: MoodPaletteScreenState,
  onEditClicked: (MoodPaletteState) -> Unit,
  onActivateClicked: (MoodPaletteState) -> Unit,
  onAddPaletteClicked: () -> Unit,
  onNameUpdated: (String) -> Unit,
  onColorAdded: () -> Unit,
  onColorRemoved: (Int) -> Unit,
  onColorSelected: (Int) -> Unit,
  onColorUpdated: (HsvColor) -> Unit,
  onSaveClicked: (MoodPaletteScreenState.EditMoodPalette) -> Unit,
  onNavigateBack: () -> Unit,
) {
  when (state) {
    is MoodPaletteScreenState.Loading -> FullScreenLoading()
    is MoodPaletteScreenState.ViewAllMoodPalettes -> ViewAllMoodPalettes(
      state = state,
      onEditClicked = onEditClicked,
      onActivateClicked = onActivateClicked,
      onAddPaletteClicked = onAddPaletteClicked,
      onNavigateBack = onNavigateBack,
    )
    is MoodPaletteScreenState.CreateMoodPalette -> CreateMoodPalette()
    is MoodPaletteScreenState.EditMoodPalette -> EditMoodPalette(
      state = state,
      onNameUpdated = onNameUpdated,
      onColorAdded = onColorAdded,
      onColorRemoved = onColorRemoved,
      onColorSelected = onColorSelected,
      onColorUpdated = onColorUpdated,
      onSaveClicked = onSaveClicked,
      onBackClicked = onNavigateBack,
    )
  }
}
