package org.dandowney.bestpartofmyday.feature.tags.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import org.dandowney.bestpartofmyday.base.BaseViewModel.Companion.SIDE_EFFECT_KEY
import org.dandowney.bestpartofmyday.design.ui.FullScreenLoading
import org.dandowney.bestpartofmyday.feature.tags.MoodTagsViewModel
import org.dandowney.bestpartofmyday.feature.tags.contract.MoodTagEvent
import org.dandowney.bestpartofmyday.feature.tags.contract.MoodTagScreenState
import org.dandowney.bestpartofmyday.feature.tags.contract.MoodTagSideEffect
import org.dandowney.bestpartofmyday.feature.today.contract.MoodTagState
import org.dandowney.bestpartofmyday.library.colorpicker.HsvColor

private sealed interface DeleteConfirmationDialogState {
  object Hidden : DeleteConfirmationDialogState
  data class Shown(val moodTagState: MoodTagState) : DeleteConfirmationDialogState
}

@Composable
fun MoodTagsScreen(
  viewModel: MoodTagsViewModel = hiltViewModel(),
  onNavigateBack: () -> Unit,
) {

  var showConfirmDeletion by remember {
    mutableStateOf<MoodTagState?>(null)
  }

  LaunchedEffect(key1 = SIDE_EFFECT_KEY) {
    viewModel.effect.collect { sideEffect ->
      when (sideEffect) {
        is MoodTagSideEffect.NavigateBack -> onNavigateBack()
      }
    }
  }
  val state by viewModel.state
  MoodTagsScreen(
    state = state,
    onMoodTagClicked = { moodTagState -> viewModel.sendEvent(MoodTagEvent.EditMoodTag(moodTagState)) },
    onAddMoodTagClicked = { viewModel.sendEvent(MoodTagEvent.AddMoodTag) },
    onNameUpdated = { name -> viewModel.sendEvent(MoodTagEvent.NameUpdated(name)) },
    onColorUpdated = { hsvColor -> viewModel.sendEvent(MoodTagEvent.ColorUpdated(hsvColor)) },
    onSaveClicked = { editState -> viewModel.sendEvent(MoodTagEvent.SavedMoodTag(editState)) },
    onBackClicked = { viewModel.sendEvent(MoodTagEvent.BackClicked) },
    onDeleteClick = { moodTagState -> viewModel.sendEvent(MoodTagEvent.DeleteClicked(moodTagState = moodTagState)) },
    onArchiveConfirm = { moodTagState -> viewModel.sendEvent(MoodTagEvent.ArchiveConfirmed(moodTagState = moodTagState)) },
    onDeleteConfirm = { moodTagState -> viewModel.sendEvent(MoodTagEvent.DeleteConfirmed(moodTagState = moodTagState)) },
    onCloseDialog = { viewModel.sendEvent(MoodTagEvent.DeleteCancelled) }
  )
}

@Composable
private fun MoodTagsScreen(
  state: MoodTagScreenState,
  onMoodTagClicked: (MoodTagState) -> Unit,
  onAddMoodTagClicked: () -> Unit,
  onNameUpdated: (String) -> Unit,
  onColorUpdated: (HsvColor) -> Unit,
  onSaveClicked: (MoodTagScreenState.EditMoodTag) -> Unit,
  onBackClicked: () -> Unit,
  onDeleteClick: (MoodTagState) -> Unit,
  onArchiveConfirm: (MoodTagState) -> Unit,
  onDeleteConfirm: (MoodTagState) -> Unit,
  onCloseDialog: () -> Unit,
) {
  when (state) {
    is MoodTagScreenState.Loading -> FullScreenLoading()
    is MoodTagScreenState.ViewAllMoodTags -> ViewAllMoodTagsScreen(
      state = state,
      onBackClicked = onBackClicked,
      onMoodTagClicked = onMoodTagClicked,
      onAddMoodTagClicked = onAddMoodTagClicked,
      onDeleteClick = onDeleteClick,
      onArchiveConfirm = onArchiveConfirm,
      onDeleteConfirm = onDeleteConfirm,
      onCloseDialog = onCloseDialog,
    )
    is MoodTagScreenState.EditMoodTag -> EditMoodTagScreen(
      state = state,
      onNameUpdated = onNameUpdated,
      onColorUpdated = onColorUpdated,
      onSaveClicked = onSaveClicked,
      onBackClicked = onBackClicked,
    )
  }
}
