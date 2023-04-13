package org.dandowney.bestpartofmyday.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import org.dandowney.bestpartofmyday.data.db.entities.DaySummaryEntity
import org.dandowney.bestpartofmyday.data.db.joins.DaySummaryWithMoodTags

@Dao
interface DaySummaryDao {

  @Query("SELECT * FROM day_summary")
  suspend fun getAll(): List<DaySummaryWithMoodTags>

  @Query("SELECT * FROM day_summary WHERE date = :date")
  suspend fun getWhere(date: Long): DaySummaryWithMoodTags?

  @Query("SELECT * FROM day_summary WHERE date BETWEEN :startSeconds AND :endSeconds")
  suspend fun getWhereDateBetween(
    startSeconds: Long,
    endSeconds: Long,
  ): List<DaySummaryWithMoodTags>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(daySummary: DaySummaryEntity): Long

  @Update
  suspend fun update(daySummary: DaySummaryEntity)
}
