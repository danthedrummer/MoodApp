package org.dandowney.bestpartofmyday.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.dandowney.bestpartofmyday.domain.models.MoodPalette

interface MoodPaletteRepository {

  suspend fun getMoodPalettes(): List<MoodPalette>

  suspend fun getMoodPalette(moodPaletteId: Long): MoodPalette?

  suspend fun getActiveMoodPalette(): MoodPalette

  fun flowOfActiveMoodPalette(): Flow<MoodPalette>

  suspend fun setActiveMoodPalette(newActivePalette: MoodPalette)

  suspend fun saveMoodPalette(moodPalette: MoodPalette)
}
