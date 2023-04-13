package org.dandowney.bestpartofmyday.feature.today.contract

import org.dandowney.bestpartofmyday.base.ViewEvent

sealed interface TodayEvent : ViewEvent {

  data class ColorSelected(val selectedColor: ULong) : TodayEvent

  data class BestPartUpdated(val bestPart: String) : TodayEvent

  object BestPartSaved : TodayEvent

  object BackClicked : TodayEvent

  object FinishClicked : TodayEvent

  data class MoodTagSelected(val moodTag: MoodTagState) : TodayEvent

  object MoodTagsSaved : TodayEvent

  object ToggleArchivedTags : TodayEvent
}
