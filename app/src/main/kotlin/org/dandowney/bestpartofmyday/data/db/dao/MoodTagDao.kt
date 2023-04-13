package org.dandowney.bestpartofmyday.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.dandowney.bestpartofmyday.data.db.entities.MoodTagEntity
import org.dandowney.bestpartofmyday.data.db.joins.MoodTagWithColor

@Dao
interface MoodTagDao {

  @Query("SELECT * FROM mood_tag WHERE archived = false")
  suspend fun getAll(): List<MoodTagWithColor>

  @Query("SELECT * FROM mood_tag WHERE mood_tag_id = :moodTagId")
  suspend fun getById(moodTagId: Long): MoodTagEntity?

  @Query("SELECT * FROM mood_tag WHERE archived = true")
  suspend fun getArchived(): List<MoodTagWithColor>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(moodTag: MoodTagEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(moodTags: List<MoodTagEntity>)

  @Delete
  suspend fun delete(moodTag: MoodTagEntity)

  @Query("DELETE FROM mood_tag WHERE mood_tag_id = :moodTagId")
  suspend fun deleteById(moodTagId: Long)
}
