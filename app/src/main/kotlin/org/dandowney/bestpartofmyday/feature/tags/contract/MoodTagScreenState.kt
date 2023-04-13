package org.dandowney.bestpartofmyday.feature.tags.contract

import org.dandowney.bestpartofmyday.base.ViewState
import org.dandowney.bestpartofmyday.feature.today.contract.MoodTagState
import org.dandowney.bestpartofmyday.library.colorpicker.HsvColor

sealed interface MoodTagScreenState : ViewState {

  object Loading : MoodTagScreenState

  data class ViewAllMoodTags(
    val activeColors: List<ULong>,
    val moodTags: List<MoodTagState>,
    val moodTagToBeDeleted: MoodTagState?,
  ) : MoodTagScreenState

  data class EditMoodTag(
    val id: Long,
    val name: String,
    val color: HsvColor,
  ) : MoodTagScreenState
}
