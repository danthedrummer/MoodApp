package org.dandowney.bestpartofmyday.domain.models

data class MonthInfo(
  val displayDate: String,
  val startSeconds: Long,
  val endSeconds: Long,
  val numberOfDays: Int,
  val firstDayWeekOffset: Int,
)
