package org.dandowney.bestpartofmyday.data.mapper

import org.dandowney.bestpartofmyday.data.db.entities.MoodTagEntity
import org.dandowney.bestpartofmyday.domain.models.MoodTag

fun MoodTag.toEntity(colorId: Long): MoodTagEntity = MoodTagEntity(
  moodTagId = moodTagId,
  name = name,
  colorId = colorId,
  archived = archived,
)

fun MoodTagEntity.toDomain(color: ULong): MoodTag = MoodTag(
  moodTagId = moodTagId,
  name = name,
  color = color,
  archived = archived,
)
