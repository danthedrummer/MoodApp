package org.dandowney.bestpartofmyday.domain.models

/**
 * Describes a set of colors which the user can use to describe their overall mood for a given day.
 */
data class MoodPalette(
  val id: Long = 0L,
  val name: String,
  val colors: List<ULong>,
  val isActive: Boolean,
)
