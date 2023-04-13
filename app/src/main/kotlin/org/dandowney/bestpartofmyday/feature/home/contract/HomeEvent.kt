package org.dandowney.bestpartofmyday.feature.home.contract

import org.dandowney.bestpartofmyday.base.ViewEvent

sealed interface HomeEvent : ViewEvent {

  object Initialize : HomeEvent
}
