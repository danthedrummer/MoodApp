package org.dandowney.bestpartofmyday.data.db.joins

import androidx.room.Embedded
import androidx.room.Relation
import org.dandowney.bestpartofmyday.data.db.entities.ColorEntity
import org.dandowney.bestpartofmyday.data.db.entities.MoodTagEntity
import org.dandowney.bestpartofmyday.domain.models.MoodTag

data class MoodTagWithColor(
  @Embedded
  val moodTagEntity: MoodTagEntity,
  @Relation(
    parentColumn = "color_id",
    entityColumn = "color_id",
  )
  val color: ColorEntity
) {

  fun toDomain(): MoodTag = MoodTag(
    moodTagId = moodTagEntity.moodTagId,
    name = moodTagEntity.name,
    color = color.value.toULong(),
  )
}