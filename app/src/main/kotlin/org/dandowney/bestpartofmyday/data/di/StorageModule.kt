package org.dandowney.bestpartofmyday.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.dandowney.bestpartofmyday.data.db.BestPartOfMyDayDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class StorageModule {

  @Provides
  @Singleton
  fun database(
    @ApplicationContext context: Context,
  ): BestPartOfMyDayDatabase {
    return Room.databaseBuilder(
      context = context,
      klass = BestPartOfMyDayDatabase::class.java,
      name = "best_part_of_my_day_database",
    ).build()
  }

  @Provides
  @Reusable
  fun daySummaryDao(database: BestPartOfMyDayDatabase) = database.daySummaryDao()

  @Provides
  @Reusable
  fun moodTagDao(database: BestPartOfMyDayDatabase) = database.moodTagDao()

  @Provides
  @Reusable
  fun moodPaletteDao(database: BestPartOfMyDayDatabase) = database.moodPaletteDao()

  @Provides
  @Reusable
  fun colorDao(database: BestPartOfMyDayDatabase) = database.colorDao()

  @Provides
  @Reusable
  fun moodPaletteColorDao(database: BestPartOfMyDayDatabase) = database.moodPaletteColorDao()

  @Provides
  @Reusable
  fun daySummaryMoodTagDao(database: BestPartOfMyDayDatabase) = database.daySummaryMoodTagDao()
}
