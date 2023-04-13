package org.dandowney.bestpartofmyday.data.db.joins

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import org.dandowney.bestpartofmyday.data.db.crossrefs.MoodPaletteColorCrossRef
import org.dandowney.bestpartofmyday.data.db.entities.ColorEntity
import org.dandowney.bestpartofmyday.data.db.entities.MoodPaletteEntity

data class MoodPaletteWithColors(
  @Embedded
  val moodPalette: MoodPaletteEntity,

  @Relation(
    parentColumn = "mood_palette_id",
    entityColumn = "color_id",
    associateBy = Junction(MoodPaletteColorCrossRef::class)
  )
  val colors: List<ColorEntity>,
)