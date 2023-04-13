package org.dandowney.bestpartofmyday.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.dandowney.bestpartofmyday.data.db.entities.MoodPaletteEntity

@Dao
interface MoodPaletteDao {

  @Query("SELECT * FROM mood_palette")
  suspend fun getAll(): List<MoodPaletteEntity>

  @Query("SELECT * FROM mood_palette WHERE mood_palette_id = :moodPaletteId")
  suspend fun getWhere(moodPaletteId: Long): MoodPaletteEntity?

  @Query("SELECT * FROM mood_palette WHERE is_active = true")
  suspend fun getActive(): MoodPaletteEntity?

  @Query("SELECT * FROM mood_palette WHERE is_active = true")
  fun flowOfActive(): Flow<MoodPaletteEntity?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(moodPalette: MoodPaletteEntity): Long

  @Update
  suspend fun update(entities: List<MoodPaletteEntity>)

  @Update
  suspend fun update(moodPalette: MoodPaletteEntity)

}
