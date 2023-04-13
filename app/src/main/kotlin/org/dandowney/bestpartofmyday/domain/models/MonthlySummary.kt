package org.dandowney.bestpartofmyday.domain.models

data class MonthlySummary(
  val displayDate: String,
  val daySummaries: List<DaySummary?>,
  val firstDayWeekOffset: Int,
)
