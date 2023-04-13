package org.dandowney.bestpartofmyday.feature.calendar.contract

import org.dandowney.bestpartofmyday.base.ViewState
import org.dandowney.bestpartofmyday.domain.models.DaySummary

sealed interface CalenderScreenState : ViewState {

  object Loading : CalenderScreenState

  data class MonthOverview(
    val displayDate: String,
    val days: List<MonthOverviewDayState>,
    val activeColorPalette: List<ULong>,
  ) : CalenderScreenState

  data class DayOverview(
    val daySummary: DaySummary
  )
}

sealed interface MonthOverviewDayState {

  object Blank : MonthOverviewDayState

  data class Populated(
    val dayNumber: String,
    val daySummary: DaySummary?,
  ) : MonthOverviewDayState
}
