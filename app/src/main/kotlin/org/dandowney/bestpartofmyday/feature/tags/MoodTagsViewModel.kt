package org.dandowney.bestpartofmyday.feature.tags

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.dandowney.bestpartofmyday.base.BaseViewModel
import org.dandowney.bestpartofmyday.data.usecase.ArchiveMoodTagUseCase
import org.dandowney.bestpartofmyday.data.usecase.DeleteMoodTagUseCase
import org.dandowney.bestpartofmyday.data.usecase.GetActiveMoodPaletteUseCase
import org.dandowney.bestpartofmyday.data.usecase.GetMoodTagsUseCase
import org.dandowney.bestpartofmyday.data.usecase.SaveMoodTagUseCase
import org.dandowney.bestpartofmyday.domain.models.MoodPalette
import org.dandowney.bestpartofmyday.domain.models.MoodTag
import org.dandowney.bestpartofmyday.feature.tags.contract.MoodTagEvent
import org.dandowney.bestpartofmyday.feature.tags.contract.MoodTagScreenState
import org.dandowney.bestpartofmyday.feature.tags.contract.MoodTagSideEffect
import org.dandowney.bestpartofmyday.feature.today.contract.MoodTagState
import org.dandowney.bestpartofmyday.library.colorpicker.HsvColor
import javax.inject.Inject

@HiltViewModel
class MoodTagsViewModel @Inject constructor(
  private val getMoodTagsUseCase: GetMoodTagsUseCase,
  private val getActiveMoodPaletteUseCase: GetActiveMoodPaletteUseCase,
  private val saveMoodTagUseCase: SaveMoodTagUseCase,
  private val archiveMoodTagUseCase: ArchiveMoodTagUseCase,
  private val deleteMoodTagUseCase: DeleteMoodTagUseCase,
) : BaseViewModel<MoodTagEvent, MoodTagScreenState, MoodTagSideEffect>() {

  private lateinit var activeMoodPalette: MoodPalette

  init {
    viewModelScope.launch {
      activeMoodPalette = getActiveMoodPaletteUseCase()
      showAllMoodTags()
    }
  }

  override fun createInitialState(): MoodTagScreenState = MoodTagScreenState.Loading

  override suspend fun handleEvent(event: MoodTagEvent) {
    when (event) {
      is MoodTagEvent.AddMoodTag -> handleAddMoodTag()
      is MoodTagEvent.EditMoodTag -> handleEditMoodTag(event.moodTagState)
      is MoodTagEvent.ColorUpdated -> handleColorUpdated(event.hsvColor)
      is MoodTagEvent.NameUpdated -> handleNameUpdated(event.name)
      is MoodTagEvent.SavedMoodTag -> handleSaveMoodTag(event.editState)
      is MoodTagEvent.BackClicked -> handleBackClicked()
      is MoodTagEvent.ArchiveConfirmed -> handleArchiveMoodTag(event.moodTagState)
      is MoodTagEvent.DeleteConfirmed -> handleDeleteMoodTag(event.moodTagState)
      is MoodTagEvent.DeleteClicked -> handleDeleteClicked(event.moodTagState)
      is MoodTagEvent.DeleteCancelled -> handleDeleteCancelled()
    }
  }

  private fun handleAddMoodTag() {
    handleEditMoodTag(
      moodTagState = MoodTagState(
        moodTag = MoodTag(
          name = "",
          color = Color.White.value,
        ),
        isSelected = false,
      )
    )
  }

  private fun handleEditMoodTag(moodTagState: MoodTagState) {
    setState {
      MoodTagScreenState.EditMoodTag(
        id = moodTagState.moodTag.moodTagId,
        name = moodTagState.moodTag.name,
        color = HsvColor.from(Color(moodTagState.moodTag.color)),
      )
    }
  }

  private fun handleNameUpdated(name: String) {
    setSealedState<MoodTagScreenState.EditMoodTag> { current ->
      current.copy(
        name = name,
      )
    }
  }

  private fun handleColorUpdated(hsvColor: HsvColor) {
    setSealedState<MoodTagScreenState.EditMoodTag> { current ->
      current.copy(
        color = hsvColor,
      )
    }
  }

  private suspend fun handleSaveMoodTag(editState: MoodTagScreenState.EditMoodTag) {
    saveMoodTagUseCase(
      moodTag = MoodTag(
        moodTagId = editState.id,
        name = editState.name,
        color = editState.color.toColor().value,
      )
    )
    showAllMoodTags()
  }

  private suspend fun showAllMoodTags() {
    val allMoodTags = getMoodTagsUseCase().map { moodTag ->
      MoodTagState(
        moodTag = moodTag,
        isSelected = false,
      )
    }
    setState {
      MoodTagScreenState.ViewAllMoodTags(
        moodTags = allMoodTags,
        activeColors = activeMoodPalette.colors,
        moodTagToBeDeleted = null,
      )
    }
  }

  private suspend fun handleBackClicked() {
    when (state.value) {
      is MoodTagScreenState.Loading,
      is MoodTagScreenState.ViewAllMoodTags,
      -> setEffect { MoodTagSideEffect.NavigateBack }
      is MoodTagScreenState.EditMoodTag -> showAllMoodTags()
    }
  }

  private fun handleDeleteClicked(moodTagState: MoodTagState) {
    setSealedState<MoodTagScreenState.ViewAllMoodTags> { current ->
      current.copy(
        moodTagToBeDeleted = moodTagState,
      )
    }
  }

  private suspend fun handleArchiveMoodTag(moodTagState: MoodTagState) {
    archiveMoodTagUseCase(moodTag = moodTagState.moodTag)
    closeDialog()
  }

  private suspend fun handleDeleteMoodTag(moodTagState: MoodTagState) {
    deleteMoodTagUseCase(moodTag = moodTagState.moodTag)
    closeDialog()
  }

  private fun handleDeleteCancelled() {
    closeDialog()
  }

  private fun closeDialog() {
    setSealedState<MoodTagScreenState.ViewAllMoodTags> { current ->
      current.copy(
        moodTagToBeDeleted = null,
      )
    }
  }
}
