package org.dandowney.bestpartofmyday.data.usecase

import dagger.Reusable
import org.dandowney.bestpartofmyday.domain.models.MoodPalette
import org.dandowney.bestpartofmyday.domain.repositories.MoodPaletteRepository
import javax.inject.Inject

@Reusable
class SaveMoodPaletteUseCase @Inject constructor(
  private val moodPaletteRepository: MoodPaletteRepository,
) {

  suspend operator fun invoke(moodPalette: MoodPalette) {
    moodPaletteRepository.saveMoodPalette(moodPalette = moodPalette)
  }
}
