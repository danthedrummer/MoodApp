package org.dandowney.bestpartofmyday.data.mapper

import org.dandowney.bestpartofmyday.data.db.entities.MoodPaletteEntity
import org.dandowney.bestpartofmyday.data.db.joins.MoodPaletteWithColors
import org.dandowney.bestpartofmyday.domain.models.MoodPalette

fun MoodPaletteEntity.toDomain(withColors: List<ULong>): MoodPalette = MoodPalette(
  id = moodPaletteId,
  name = name,
  colors = withColors,
  isActive = isActive,
)

fun MoodPaletteWithColors.toDomain(): MoodPalette = moodPalette.toDomain(
  withColors = colors.map { it.value.toULong() },
)

fun MoodPalette.toEntity(): MoodPaletteEntity = MoodPaletteEntity(
  moodPaletteId = id,
  name = name,
  isActive = isActive,
)
