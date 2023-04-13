package org.dandowney.bestpartofmyday.data.repositories

import org.dandowney.bestpartofmyday.data.db.TransactionHandler
import org.dandowney.bestpartofmyday.data.db.crossrefs.DaySummaryMoodTagCrossRef
import org.dandowney.bestpartofmyday.data.db.dao.DaySummaryDao
import org.dandowney.bestpartofmyday.data.db.dao.DaySummaryMoodTagDao
import org.dandowney.bestpartofmyday.data.db.entities.DaySummaryEntity
import org.dandowney.bestpartofmyday.data.db.joins.DaySummaryWithMoodTags
import org.dandowney.bestpartofmyday.domain.models.DaySummary
import org.dandowney.bestpartofmyday.domain.repositories.DaySummaryRepository
import javax.inject.Inject

internal class DaySummaryDatabaseRepository @Inject constructor(
  private val transactionHandler: TransactionHandler,
  private val daySummaryDao: DaySummaryDao,
  private val daySummaryMoodTagDao: DaySummaryMoodTagDao,
) : DaySummaryRepository {

  override suspend fun getDaySummaries(): List<DaySummary> {
    return daySummaryDao.getAll()
      .map { it.toDomain() }
  }

  override suspend fun getDaySummaryFor(epochSeconds: Long): DaySummary? {
    return daySummaryDao.getWhere(epochSeconds)?.toDomain()
  }

  override suspend fun getDaySummariesBetween(
    startSeconds: Long,
    endSeconds: Long,
  ): List<DaySummary> {
    return daySummaryDao.getWhereDateBetween(
      startSeconds = startSeconds,
      endSeconds = endSeconds,
    ).map {
      it.toDomain()
    }
  }

  override suspend fun saveDaySummary(daySummary: DaySummary) {
    transactionHandler {
      val existingSummary = daySummaryDao.getWhere(daySummary.date)
      if (existingSummary != null) {
        updateExistingSummary(daySummary, existingSummary)
      } else {
        createNewSummary(daySummary)
      }
    }
  }

  private suspend fun updateExistingSummary(daySummary: DaySummary, existingSummary: DaySummaryWithMoodTags) {
    val entityToUpdate = existingSummary.daySummaryEntity.copy(
      color = daySummary.color.toLong(),
      bestPart = daySummary.bestPart,
    )

    val selectedTagIds = daySummary.tags.map { it.moodTagId }
    val existingTagIds = existingSummary.moodTags.map { it.moodTagEntity.moodTagId }

    val tagsToDelete = existingSummary.moodTags
      .filter { it.moodTagEntity.moodTagId !in selectedTagIds }
      .map {
        DaySummaryMoodTagCrossRef(
          daySummaryId = entityToUpdate.daySummaryId,
          moodTagId = it.moodTagEntity.moodTagId,
        )
      }

    val tagsToInsert = daySummary.tags
      .filter { it.moodTagId !in existingTagIds }
      .map {
        DaySummaryMoodTagCrossRef(
          daySummaryId = entityToUpdate.daySummaryId,
          moodTagId = it.moodTagId,
        )
      }

    daySummaryDao.update(entityToUpdate)
    daySummaryMoodTagDao.insert(tagsToInsert)
    daySummaryMoodTagDao.delete(tagsToDelete)
  }

  private suspend fun createNewSummary(daySummary: DaySummary) {
    val daySummaryEntity = DaySummaryEntity(
      color = daySummary.color.toLong(),
      date = daySummary.date,
      bestPart = daySummary.bestPart,
    )
    val summaryId = daySummaryDao.insert(daySummaryEntity)

    val crossRefs = daySummary.tags.map {
      DaySummaryMoodTagCrossRef(
        daySummaryId = summaryId,
        moodTagId = it.moodTagId,
      )
    }
    daySummaryMoodTagDao.insert(crossRefs)
  }
}
