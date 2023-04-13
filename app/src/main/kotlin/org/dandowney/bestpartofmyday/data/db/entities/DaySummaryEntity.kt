package org.dandowney.bestpartofmyday.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
  tableName = "day_summary",
  indices = [Index("date", unique = true)]
)
data class DaySummaryEntity(

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "day_summary_id")
  val daySummaryId: Long = 0L,

  val color: Long,
  val date: Long,
  @ColumnInfo(name = "best_part") val bestPart: String,
)