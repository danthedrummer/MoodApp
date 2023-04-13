package org.dandowney.bestpartofmyday.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import org.dandowney.bestpartofmyday.data.db.crossrefs.DaySummaryMoodTagCrossRef
import org.dandowney.bestpartofmyday.data.db.crossrefs.MoodPaletteColorCrossRef
import org.dandowney.bestpartofmyday.data.db.dao.ColorDao
import org.dandowney.bestpartofmyday.data.db.dao.DaySummaryDao
import org.dandowney.bestpartofmyday.data.db.dao.DaySummaryMoodTagDao
import org.dandowney.bestpartofmyday.data.db.dao.MoodPaletteColorsDao
import org.dandowney.bestpartofmyday.data.db.dao.MoodPaletteDao
import org.dandowney.bestpartofmyday.data.db.dao.MoodTagDao
import org.dandowney.bestpartofmyday.data.db.entities.ColorEntity
import org.dandowney.bestpartofmyday.data.db.entities.DaySummaryEntity
import org.dandowney.bestpartofmyday.data.db.entities.MoodPaletteEntity
import org.dandowney.bestpartofmyday.data.db.entities.MoodTagEntity

@Database(
  entities = [
    // entities
    ColorEntity::class,
    DaySummaryEntity::class,
    MoodPaletteEntity::class,
    MoodTagEntity::class,

    // cross refs
    DaySummaryMoodTagCrossRef::class,
    MoodPaletteColorCrossRef::class,
  ],
  version = 1,
)
internal abstract class BestPartOfMyDayDatabase : RoomDatabase() {

  abstract fun daySummaryDao(): DaySummaryDao

  abstract fun moodPaletteDao(): MoodPaletteDao

  abstract fun moodTagDao(): MoodTagDao

  abstract fun colorDao(): ColorDao

  abstract fun moodPaletteColorDao(): MoodPaletteColorsDao

  abstract fun daySummaryMoodTagDao(): DaySummaryMoodTagDao
}