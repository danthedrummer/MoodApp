package org.dandowney.bestpartofmyday.feature.today.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import org.dandowney.bestpartofmyday.design.theme.Blue300
import org.dandowney.bestpartofmyday.design.theme.Green300
import org.dandowney.bestpartofmyday.design.theme.Purple300
import org.dandowney.bestpartofmyday.design.theme.Red300
import org.dandowney.bestpartofmyday.design.theme.Yellow300
import org.dandowney.bestpartofmyday.design.ui.FullScreenLoading
import org.dandowney.bestpartofmyday.feature.today.TodayViewModel
import org.dandowney.bestpartofmyday.feature.today.contract.MoodTagState
import org.dandowney.bestpartofmyday.feature.today.contract.TodayEvent
import org.dandowney.bestpartofmyday.feature.today.contract.TodayScreenState
import org.dandowney.bestpartofmyday.feature.today.contract.TodaySideEffect

/**
 * TODO: Clean up edit navigation
 *   The back button should always exit the screen and I need a different mechanism
 *   for navigating back through editing the day. Maybe a bottom bar with progress?
 *   e.g.
 *   ```
 *   [color]---[best part]---[tags]---[summary]
 *   ```
 */
@Composable
fun Today(
  viewModel: TodayViewModel = hiltViewModel(),
  onNavigateBack: () -> Unit,
) {

  LaunchedEffect(key1 = "view-model-side-effects") {
    viewModel.effect.collect { sideEffect ->
      when (sideEffect) {
        TodaySideEffect.Navigation.Back -> onNavigateBack()
      }
    }
  }

  val state by viewModel.state
  Today(
    state = state,
    onColorSelected = { selectedColor -> viewModel.sendEvent(TodayEvent.ColorSelected(selectedColor)) },
    onBackClicked = { viewModel.sendEvent(TodayEvent.BackClicked) },
    onFinishClicked = { viewModel.sendEvent(TodayEvent.FinishClicked) },
    onBestPartUpdated = { bestPart -> viewModel.sendEvent(TodayEvent.BestPartUpdated(bestPart)) },
    onBestPartSaved = { viewModel.sendEvent(TodayEvent.BestPartSaved) },
    onTagSelected = { moodTag -> viewModel.sendEvent(TodayEvent.MoodTagSelected(moodTag)) },
    onTagsSaved = { viewModel.sendEvent(TodayEvent.MoodTagsSaved) },
    onNavigateBack = onNavigateBack,
    onToggleArchivedTags = { viewModel.sendEvent(TodayEvent.ToggleArchivedTags) }
  )
}

@Composable
private fun Today(
  state: TodayScreenState,
  onColorSelected: (ULong) -> Unit,
  onBestPartUpdated: (String) -> Unit,
  onBestPartSaved: () -> Unit,
  onTagSelected: (MoodTagState) -> Unit,
  onTagsSaved: () -> Unit,
  onBackClicked: () -> Unit,
  onFinishClicked: () -> Unit,
  onNavigateBack: () -> Unit,
  onToggleArchivedTags: () -> Unit,
) {
  when (state) {
    is TodayScreenState.Loading -> FullScreenLoading()
    is TodayScreenState.SelectColor -> SelectColor(
      state = state,
      onColorSelected = onColorSelected,
      onNavigateBack = onNavigateBack,
    )
    is TodayScreenState.ShareBestPart -> ShareBestPart(
      state = state,
      onBackClicked = onBackClicked,
      onBestPartUpdated = onBestPartUpdated,
      onBestPartSaved = onBestPartSaved,
    )
    is TodayScreenState.AddMoodTags -> AddMoodTags(
      state = state,
      onTagSelected = onTagSelected,
      onTagsSaved = onTagsSaved,
      onBackClicked = onBackClicked,
      onToggleArchivedTags = onToggleArchivedTags,
    )
    is TodayScreenState.Summarize -> Summarize(
      state = state,
      onBackClicked = onBackClicked,
      onFinishClicked = onFinishClicked,
    )
  }
}

@Preview(name = "Select color", showBackground = true)
@Composable
private fun SelectColorPreview() {
  SelectColor(
    state = TodayScreenState.SelectColor(
      colorPalette = listOf(
        Red300.value,
        Green300.value,
        Blue300.value,
        Purple300.value,
        Yellow300.value,
      ),
      selectedColor = null,
    ),
    onColorSelected = {},
    onNavigateBack = {},
  )
}

@Preview(name = "Share best part", showBackground = true)
@Composable
private fun ShareBestPartPreview() {
  ShareBestPart(
    state = TodayScreenState.ShareBestPart(
      backgroundColor = Purple300.value,
      bestPart = "",
      shouldAnimate = false,
    ),
    onBackClicked = {},
    onBestPartUpdated = {},
    onBestPartSaved = {},
  )
}
