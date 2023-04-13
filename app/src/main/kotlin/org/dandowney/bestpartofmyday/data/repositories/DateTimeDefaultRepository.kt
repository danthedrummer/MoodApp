package org.dandowney.bestpartofmyday.data.repositories

import dagger.Reusable
import org.dandowney.bestpartofmyday.domain.models.MonthInfo
import org.dandowney.bestpartofmyday.domain.repositories.DateTimeRepository
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@Reusable
class DateTimeDefaultRepository @Inject constructor() : DateTimeRepository {

  private val currentDate: Date = Date()
  private var currentEpochSeconds: Long = currentDate.time
  private var currentDateString: String = SimpleDateFormat.getDateInstance().format(currentDate.time)

  override fun getCurrentDateString(): String = currentDateString

  override fun getCurrentDayEpochSeconds(): Long = currentEpochSeconds

  override fun getMonthInfo(monthOffset: Long): MonthInfo {
    TODO("Not yet implemented")
  }

  override fun dayNumberFromEpochSeconds(epochSeconds: Long): Int {
    TODO("Not yet implemented")
  }
}
