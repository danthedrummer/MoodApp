package org.dandowney.bestpartofmyday.data.db.crossrefs

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
  tableName = "day_summary_mood_tag",
  primaryKeys = ["day_summary_id", "mood_tag_id"],
  indices = [Index("mood_tag_id")],
)
data class DaySummaryMoodTagCrossRef(
  @ColumnInfo(name = "day_summary_id") val daySummaryId: Long,
  @ColumnInfo(name = "mood_tag_id") val moodTagId: Long,
)