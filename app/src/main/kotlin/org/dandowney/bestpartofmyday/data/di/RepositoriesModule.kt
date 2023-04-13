package org.dandowney.bestpartofmyday.data.di

import android.os.Build
import androidx.compose.runtime.Composable
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.dandowney.bestpartofmyday.data.repositories.DateTimeDefaultRepository
import org.dandowney.bestpartofmyday.data.repositories.DateTimeOreoRepository
import org.dandowney.bestpartofmyday.data.repositories.DaySummaryDatabaseRepository
import org.dandowney.bestpartofmyday.data.repositories.MoodPaletteDatabaseRepository
import org.dandowney.bestpartofmyday.data.repositories.MoodTagDatabaseRepository
import org.dandowney.bestpartofmyday.domain.repositories.DateTimeRepository
import org.dandowney.bestpartofmyday.domain.repositories.DaySummaryRepository
import org.dandowney.bestpartofmyday.domain.repositories.MoodPaletteRepository
import org.dandowney.bestpartofmyday.domain.repositories.MoodTagRepository

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoriesModule {

  @Binds
  @Reusable
  fun daySummaryRepository(databaseImpl: DaySummaryDatabaseRepository): DaySummaryRepository

  @Binds
  @Reusable
  fun moodTagRepository(databaseImpl: MoodTagDatabaseRepository): MoodTagRepository

  @Binds
  @Reusable
  fun moodPaletteRepository(databaseImpl: MoodPaletteDatabaseRepository): MoodPaletteRepository

  companion object {

    @Provides
    fun dateTimeRepository(): DateTimeRepository =
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        DateTimeOreoRepository()
      } else {
        DateTimeDefaultRepository()
      }
  }
}
