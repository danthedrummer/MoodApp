package org.dandowney.bestpartofmyday.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import org.dandowney.bestpartofmyday.domain.models.MoodTag

@Entity(
  tableName = "mood_tag",
  foreignKeys = [
    ForeignKey(
      entity = ColorEntity::class,
      parentColumns = ["color_id"],
      childColumns = ["color_id"],
      onDelete = CASCADE,
    )
  ],
  indices = [
    Index("color_id"),
  ],
)
data class MoodTagEntity(

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "mood_tag_id")
  val moodTagId: Long = 0L,

  @ColumnInfo(name = "color_id")
  val colorId: Long,

  val name: String,
  val archived: Boolean = false,
)
