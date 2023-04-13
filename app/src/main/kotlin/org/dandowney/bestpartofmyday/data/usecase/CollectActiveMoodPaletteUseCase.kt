package org.dandowney.bestpartofmyday.data.usecase

import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import org.dandowney.bestpartofmyday.domain.models.MoodPalette
import org.dandowney.bestpartofmyday.domain.repositories.MoodPaletteRepository
import javax.inject.Inject

@Reusable
class CollectActiveMoodPaletteUseCase @Inject constructor(
  private val moodPaletteRepository: MoodPaletteRepository,
) {

  operator fun invoke(): Flow<MoodPalette> = moodPaletteRepository.flowOfActiveMoodPalette()
}
