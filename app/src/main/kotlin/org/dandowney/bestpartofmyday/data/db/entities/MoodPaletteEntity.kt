package org.dandowney.bestpartofmyday.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mood_palette")
data class MoodPaletteEntity(

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "mood_palette_id")
  val moodPaletteId: Long = 0L,

  val name: String,
  @ColumnInfo(name = "is_active") val isActive: Boolean = true,
)
