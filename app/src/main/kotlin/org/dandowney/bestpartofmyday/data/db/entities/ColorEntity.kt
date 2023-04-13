package org.dandowney.bestpartofmyday.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
  tableName = "color",
  indices = [Index("value", unique = true)]
)
data class ColorEntity(

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "color_id")
  val colorId: Long = 0L,

  val value: Long,

)