package org.dandowney.bestpartofmyday.data.usecase

import android.os.Build
import dagger.Reusable
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit
import java.util.*
import javax.inject.Inject

@Reusable
class GetDateUseCase @Inject constructor() {

  private var epochSeconds: Long = 0L
  private var dateString: String = ""

  fun getEpochSeconds(): Long {
    if (epochSeconds == 0L) prepareData()
    return epochSeconds
  }

  fun getDateString(): String {
    if (dateString.isEmpty()) prepareData()
    return dateString
  }

  private fun prepareData() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val date = LocalDateTime.now()
      epochSeconds = date.truncatedTo(ChronoUnit.DAYS).toEpochSecond(ZoneOffset.UTC)
      dateString = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
    } else {
      val date = Date()
      epochSeconds = date.time
      dateString = SimpleDateFormat.getDateInstance().format(date.time)
    }
  }
}
