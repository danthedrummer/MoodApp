package org.dandowney.bestpartofmyday.domain.models

data class DaySummary(
  val daySummaryId: Long = 0L,
  val date: Long,
  val color: ULong,
  val bestPart: String,
  val tags: List<MoodTag>,
)
