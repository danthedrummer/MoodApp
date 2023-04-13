package org.dandowney.bestpartofmyday.feature.calendar.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import org.dandowney.bestpartofmyday.design.ui.FullScreenLoading
import org.dandowney.bestpartofmyday.feature.calendar.CalenderViewModel
import org.dandowney.bestpartofmyday.feature.calendar.composable.MonthOverview
import org.dandowney.bestpartofmyday.feature.calendar.contract.CalendarEvent
import org.dandowney.bestpartofmyday.feature.calendar.contract.CalenderScreenState

@Composable
fun Calendar(
  viewModel: CalenderViewModel = hiltViewModel(),
  onNavigateBack: () -> Unit,
) {
  val state by viewModel.state
  Calendar(
    state = state,
    onNavigateBack = onNavigateBack,
    onPreviousMonthClicked = { viewModel.sendEvent(CalendarEvent.PreviousMonthClicked) },
    onNextMonthClicked = { viewModel.sendEvent(CalendarEvent.NextMonthClicked) },
  )
}

@Composable
fun Calendar(
  state: CalenderScreenState,
  onNavigateBack: () -> Unit,
  onPreviousMonthClicked: () -> Unit,
  onNextMonthClicked: () -> Unit,
) {
  when (state) {
    is CalenderScreenState.Loading -> FullScreenLoading()
    is CalenderScreenState.MonthOverview -> MonthOverview(
      state = state,
      onNavigateBack = onNavigateBack,
      onPreviousMonthClicked = onPreviousMonthClicked,
      onNextMonthClicked = onNextMonthClicked,
    )
  }
}
