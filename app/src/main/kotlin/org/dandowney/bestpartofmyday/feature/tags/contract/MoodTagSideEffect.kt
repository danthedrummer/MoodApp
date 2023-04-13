package org.dandowney.bestpartofmyday.feature.tags.contract

import org.dandowney.bestpartofmyday.base.ViewSideEffect

sealed interface MoodTagSideEffect : ViewSideEffect {

  object NavigateBack : MoodTagSideEffect
}
