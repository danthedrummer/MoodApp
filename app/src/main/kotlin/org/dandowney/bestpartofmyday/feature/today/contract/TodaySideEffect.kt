package org.dandowney.bestpartofmyday.feature.today.contract

import org.dandowney.bestpartofmyday.base.ViewSideEffect

sealed interface TodaySideEffect : ViewSideEffect {

  sealed interface Navigation : TodaySideEffect {

    object Back : Navigation
  }
}