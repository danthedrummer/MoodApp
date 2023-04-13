package org.dandowney.bestpartofmyday.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.dandowney.bestpartofmyday.data.db.crossrefs.DaySummaryMoodTagCrossRef

@Dao
interface DaySummaryMoodTagDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insert(crossRefs: List<DaySummaryMoodTagCrossRef>)

  @Delete
  suspend fun delete(crossRefs: List<DaySummaryMoodTagCrossRef>)

  @Query(
    """
    DELETE FROM day_summary_mood_tag 
    WHERE mood_tag_id = :moodTagId
    """
  )
  suspend fun deleteWhere(moodTagId: Long)

}
