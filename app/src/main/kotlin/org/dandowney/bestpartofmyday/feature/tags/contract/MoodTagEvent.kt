package org.dandowney.bestpartofmyday.feature.tags.contract

import org.dandowney.bestpartofmyday.base.ViewEvent
import org.dandowney.bestpartofmyday.feature.today.contract.MoodTagState
import org.dandowney.bestpartofmyday.library.colorpicker.HsvColor

sealed interface MoodTagEvent : ViewEvent {

  object AddMoodTag : MoodTagEvent

  data class EditMoodTag(val moodTagState: MoodTagState) : MoodTagEvent

  data class NameUpdated(val name: String) : MoodTagEvent

  data class ColorUpdated(val hsvColor: HsvColor) : MoodTagEvent

  data class SavedMoodTag(val editState: MoodTagScreenState.EditMoodTag) : MoodTagEvent

  object BackClicked : MoodTagEvent

  data class DeleteClicked(val moodTagState: MoodTagState) : MoodTagEvent

  data class DeleteConfirmed(val moodTagState: MoodTagState) : MoodTagEvent

  data class ArchiveConfirmed(val moodTagState: MoodTagState) : MoodTagEvent

  object DeleteCancelled : MoodTagEvent
}
