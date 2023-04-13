package org.dandowney.bestpartofmyday.data.db.crossrefs

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
  tableName = "mood_palette_color",
  primaryKeys = ["mood_palette_id", "color_id"],
  indices = [Index("color_id")]
)
data class MoodPaletteColorCrossRef(
  @ColumnInfo(name = "mood_palette_id") val moodPaletteId: Long,
  @ColumnInfo(name = "color_id") val colorId: Long,
  val position: Int,
)
