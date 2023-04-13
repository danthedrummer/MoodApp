package org.dandowney.bestpartofmyday.data.db.joins

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import org.dandowney.bestpartofmyday.data.db.crossrefs.DaySummaryMoodTagCrossRef
import org.dandowney.bestpartofmyday.data.db.entities.DaySummaryEntity
import org.dandowney.bestpartofmyday.data.db.entities.MoodTagEntity
import org.dandowney.bestpartofmyday.domain.models.DaySummary

data class DaySummaryWithMoodTags(
  @Embedded
  val daySummaryEntity: DaySummaryEntity,

  @Relation(
    entity = MoodTagEntity::class,
    parentColumn = "day_summary_id",
    entityColumn = "mood_tag_id",
    associateBy = Junction(DaySummaryMoodTagCrossRef::class)
  )
  val moodTags: List<MoodTagWithColor>,
) {

  fun toDomain(): DaySummary = DaySummary(
    daySummaryId = daySummaryEntity.daySummaryId,
    date = daySummaryEntity.date,
    color = daySummaryEntity.color.toULong(),
    bestPart = daySummaryEntity.bestPart,
    tags = moodTags.map { it.toDomain() },
  )
}