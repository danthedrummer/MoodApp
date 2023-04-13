package org.dandowney.bestpartofmyday.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import org.dandowney.bestpartofmyday.data.db.crossrefs.MoodPaletteColorCrossRef
import org.dandowney.bestpartofmyday.data.db.entities.ColorEntity

@Dao
interface MoodPaletteColorsDao {

  /**
   * Performing a JOIN to allow the query to order the colors based on the position
   * field provided by the cross ref table. This allows us to ensure that the colors
   * retain the order chosen by the user without giving the palette or color
   * responsibility over this field.
   */
  @Query(
    """
    SELECT color.* FROM mood_palette_color 
    JOIN color on color.color_id = mood_palette_color.color_id 
    WHERE mood_palette_color.mood_palette_id = :moodPaletteId 
    ORDER BY mood_palette_color.position ASC
    """
  )
  suspend fun getOrderedColorsWhere(moodPaletteId: Long): List<ColorEntity>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insert(crossRefs: List<MoodPaletteColorCrossRef>)

  @Delete
  suspend fun delete(crossRefs: List<MoodPaletteColorCrossRef>)

  @Query("SELECT * FROM mood_palette_color WHERE mood_palette_id = :moodPaletteId")
  suspend fun getWhere(moodPaletteId: Long): List<MoodPaletteColorCrossRef>

  @Update
  suspend fun update(crossRefs: List<MoodPaletteColorCrossRef>)
}
