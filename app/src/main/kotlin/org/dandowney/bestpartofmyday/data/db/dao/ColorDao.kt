package org.dandowney.bestpartofmyday.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.dandowney.bestpartofmyday.data.db.entities.ColorEntity

@Dao
interface ColorDao {

  @Query("SELECT * FROM color WHERE value = :value")
  suspend fun getWhere(value: Long): ColorEntity?

  @Query("SELECT * FROM color WHERE value IN (:values)")
  suspend fun getWhere(values: List<Long>): List<ColorEntity>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insert(color: ColorEntity): Long

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insert(colors: List<ColorEntity>): List<Long>
}
