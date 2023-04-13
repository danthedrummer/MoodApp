package org.dandowney.bestpartofmyday.data.usecase

import dagger.Reusable
import org.dandowney.bestpartofmyday.domain.models.DaySummary
import org.dandowney.bestpartofmyday.domain.models.MonthlySummary
import org.dandowney.bestpartofmyday.domain.repositories.DateTimeRepository
import org.dandowney.bestpartofmyday.domain.repositories.DaySummaryRepository
import javax.inject.Inject

@Reusable
class GetMonthlyDaySummariesUseCase @Inject constructor(
  private val daySummaryRepository: DaySummaryRepository,
  private val dateTimeRepository: DateTimeRepository,
) {

  suspend operator fun invoke(monthOffset: Long): MonthlySummary {
    val monthInfo = dateTimeRepository.getMonthInfo(monthOffset)
    val summariesForMonth = daySummaryRepository.getDaySummariesBetween(monthInfo.startSeconds, monthInfo.endSeconds)

    val breakdown = MutableList<DaySummary?>(monthInfo.numberOfDays) {
      null
    }
    summariesForMonth.forEach { summary ->
      breakdown[dateTimeRepository.dayNumberFromEpochSeconds(summary.date) - 1] = summary
    }

    return MonthlySummary(
      displayDate = monthInfo.displayDate,
      daySummaries = breakdown,
      firstDayWeekOffset = monthInfo.firstDayWeekOffset,
    )
  }
}
