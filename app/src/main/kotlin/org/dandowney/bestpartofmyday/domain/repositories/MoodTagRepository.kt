package org.dandowney.bestpartofmyday.domain.repositories

import org.dandowney.bestpartofmyday.domain.models.MoodTag

interface MoodTagRepository {

  suspend fun getMoodTags(): List<MoodTag>

  suspend fun getArchivedMoodTags(): List<MoodTag>

  suspend fun saveMoodTag(moodTag: MoodTag)

  suspend fun saveMoodTags(moodTags: List<MoodTag>)

  suspend fun archiveMoodTag(moodTag: MoodTag)

  suspend fun deleteMoodTag(moodTag: MoodTag)
}
