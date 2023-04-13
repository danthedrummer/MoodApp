package org.dandowney.bestpartofmyday.domain.repositories

import org.dandowney.bestpartofmyday.domain.models.MonthInfo

interface DateTimeRepository {

  fun getCurrentDateString(): String

  fun getCurrentDayEpochSeconds(): Long

  fun getMonthInfo(monthOffset: Long): MonthInfo

  fun dayNumberFromEpochSeconds(epochSeconds: Long): Int
}
