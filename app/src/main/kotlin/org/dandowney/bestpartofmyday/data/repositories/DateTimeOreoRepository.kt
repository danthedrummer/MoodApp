package org.dandowney.bestpartofmyday.data.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import dagger.Reusable
import org.dandowney.bestpartofmyday.domain.models.MonthInfo
import org.dandowney.bestpartofmyday.domain.repositories.DateTimeRepository
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@Reusable
@RequiresApi(Build.VERSION_CODES.O)
class DateTimeOreoRepository @Inject constructor() : DateTimeRepository {

  private val currentDate: LocalDateTime = LocalDateTime.now()
  private val currentEpochSeconds: Long = currentDate.truncatedTo(ChronoUnit.DAYS).toEpochSecond(ZoneOffset.UTC)
  private val currentDateString: String = currentDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))

  override fun getCurrentDateString(): String = currentDateString

  override fun getCurrentDayEpochSeconds(): Long = currentEpochSeconds

  override fun getMonthInfo(monthOffset: Long): MonthInfo {
    val selectedMonth = currentDate.minusMonths(monthOffset)
    val firstDay = selectedMonth.withDayOfMonth(1)
    val lastDay = selectedMonth.with(TemporalAdjusters.lastDayOfMonth())
    return MonthInfo(
      displayDate = selectedMonth.format(DateTimeFormatter.ofPattern("MMMM, y")),
      startSeconds = firstDay.dayEpochSecond(),
      endSeconds = lastDay.dayEpochSecond(),
      numberOfDays = lastDay.dayOfMonth,
      firstDayWeekOffset = firstDay.dayOfWeek.value,
    )
  }

  override fun dayNumberFromEpochSeconds(epochSeconds: Long): Int {
    return LocalDateTime.ofEpochSecond(epochSeconds, 0, ZoneOffset.UTC).dayOfMonth
  }

  private fun LocalDateTime.dayEpochSecond(): Long = truncatedTo(ChronoUnit.DAYS).toEpochSecond(ZoneOffset.UTC)

}
