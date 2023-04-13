package org.dandowney.bestpartofmyday.feature.today.contract

import org.dandowney.bestpartofmyday.base.ViewState
import org.dandowney.bestpartofmyday.domain.models.MoodTag

sealed interface TodayScreenState : ViewState {

  object Loading : TodayScreenState

  data class SelectColor(
    val colorPalette: List<ULong>,
    val selectedColor: ULong?,
  ) : TodayScreenState

  data class ShareBestPart(
    val backgroundColor: ULong,
    val bestPart: String,
    val shouldAnimate: Boolean,
  ) : TodayScreenState

  data class AddMoodTags(
    val backgroundColor: ULong,
    val possibleTags: List<MoodTagState>,
    val shouldShowArchived: Boolean,
    val archivedTags: List<MoodTagState>,
  ) : TodayScreenState

  data class Summarize(
    val backgroundColor: ULong,
    val date: String,
    val bestPart: String,
    val selectedTags: List<MoodTagState>,
  ) : TodayScreenState
}

data class MoodTagState(
  val moodTag: MoodTag,
  val isSelected: Boolean,
)
