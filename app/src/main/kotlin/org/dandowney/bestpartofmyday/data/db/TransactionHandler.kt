package org.dandowney.bestpartofmyday.data.db

import androidx.room.withTransaction
import javax.inject.Inject

internal class TransactionHandler @Inject constructor(
  private val database: BestPartOfMyDayDatabase,
) {

  suspend operator fun <T> invoke(block: suspend () -> T): T = database.withTransaction(block)
}