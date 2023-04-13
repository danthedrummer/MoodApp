package org.dandowney.bestpartofmyday.feature.calendar

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.dandowney.bestpartofmyday.base.BaseViewModel
import org.dandowney.bestpartofmyday.data.usecase.GetActiveMoodPaletteUseCase
import org.dandowney.bestpartofmyday.data.usecase.GetMonthlyDaySummariesUseCase
import org.dandowney.bestpartofmyday.feature.calendar.contract.CalendarEvent
import org.dandowney.bestpartofmyday.feature.calendar.contract.CalenderScreenState
import org.dandowney.bestpartofmyday.feature.calendar.contract.CalenderSideEffect
import org.dandowney.bestpartofmyday.feature.calendar.contract.MonthOverviewDayState
import javax.inject.Inject

@HiltViewModel
class CalenderViewModel @Inject constructor(
  private val getMonthlyDaySummariesUseCase: GetMonthlyDaySummariesUseCase,
  private val getActiveMoodPaletteUseCase: GetActiveMoodPaletteUseCase,
) : BaseViewModel<CalendarEvent, CalenderScreenState, CalenderSideEffect>() {

  private var currentOffset: Long = 0L

  init {
    viewModelScope.launch {
      loadMonth(currentOffset)
    }
  }

  override fun createInitialState(): CalenderScreenState = CalenderScreenState.Loading

  override suspend fun handleEvent(event: CalendarEvent) {
    when (event) {
      CalendarEvent.NextMonthClicked -> loadMonth(--currentOffset)
      CalendarEvent.PreviousMonthClicked -> loadMonth(++currentOffset)
    }
  }

  private suspend fun loadMonth(offset: Long) {
    val monthlySummary = getMonthlyDaySummariesUseCase(offset)
    val activeColors = getActiveMoodPaletteUseCase().colors
    setState {
      CalenderScreenState.MonthOverview(
        displayDate = monthlySummary.displayDate,
        activeColorPalette = activeColors,
        days = buildList {
          repeat(monthlySummary.firstDayWeekOffset - 1) {
            add(MonthOverviewDayState.Blank)
          }
          monthlySummary.daySummaries.mapIndexed { index, summary ->
            MonthOverviewDayState.Populated(
              dayNumber = (index + 1).toString(),
              daySummary = summary,
            )
          }.let(::addAll)
          while (size % 7 != 0) {
            add(MonthOverviewDayState.Blank)
          }
        }
      )
    }
  }
}
