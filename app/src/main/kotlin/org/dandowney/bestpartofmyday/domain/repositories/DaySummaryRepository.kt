package org.dandowney.bestpartofmyday.domain.repositories

import org.dandowney.bestpartofmyday.domain.models.DaySummary

interface DaySummaryRepository {

  suspend fun getDaySummaries(): List<DaySummary>

  suspend fun getDaySummaryFor(epochSeconds: Long): DaySummary?

  suspend fun getDaySummariesBetween(startSeconds: Long, endSeconds: Long): List<DaySummary>

  suspend fun saveDaySummary(daySummary: DaySummary)
}
