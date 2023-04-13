package org.dandowney.bestpartofmyday.data.usecase

import dagger.Reusable
import org.dandowney.bestpartofmyday.domain.models.MoodTag
import org.dandowney.bestpartofmyday.domain.repositories.MoodTagRepository
import javax.inject.Inject

@Reusable
class ArchiveMoodTagUseCase @Inject constructor(
  private val moodTagRepository: MoodTagRepository,
) {

  suspend operator fun invoke(moodTag: MoodTag) {
    moodTagRepository.archiveMoodTag(moodTag = moodTag)
  }
}
