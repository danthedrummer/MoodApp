package org.dandowney.bestpartofmyday.domain.models

/**
 * Describes a tag which a user can use to describe a feeling for a given day. A day can be
 * described by multiple mood tags.
 */
data class MoodTag(
  val moodTagId: Long = 0L,
  val name: String,
  val color: ULong,
  val archived: Boolean = false,
)