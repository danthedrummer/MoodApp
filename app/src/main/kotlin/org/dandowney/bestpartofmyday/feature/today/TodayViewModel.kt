package org.dandowney.bestpartofmyday.feature.today

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.dandowney.bestpartofmyday.base.BaseViewModel
import org.dandowney.bestpartofmyday.data.usecase.GetActiveMoodPaletteUseCase
import org.dandowney.bestpartofmyday.data.usecase.GetArchivedMoodTagsUseCase
import org.dandowney.bestpartofmyday.data.usecase.GetDateUseCase
import org.dandowney.bestpartofmyday.data.usecase.GetMoodTagsUseCase
import org.dandowney.bestpartofmyday.domain.models.DaySummary
import org.dandowney.bestpartofmyday.domain.models.MoodPalette
import org.dandowney.bestpartofmyday.domain.repositories.DaySummaryRepository
import org.dandowney.bestpartofmyday.feature.today.contract.MoodTagState
import org.dandowney.bestpartofmyday.feature.today.contract.TodayEvent
import org.dandowney.bestpartofmyday.feature.today.contract.TodayScreenState
import org.dandowney.bestpartofmyday.feature.today.contract.TodaySideEffect
import javax.inject.Inject

@HiltViewModel
class TodayViewModel @Inject constructor(
  private val daySummaryRepository: DaySummaryRepository,
  private val getDateUseCase: GetDateUseCase,
  private val getMoodTagsUseCase: GetMoodTagsUseCase,
  private val getActiveMoodPaletteUseCase: GetActiveMoodPaletteUseCase,
  private val getArchivedMoodTagsUseCase: GetArchivedMoodTagsUseCase,
) : BaseViewModel<TodayEvent, TodayScreenState, TodaySideEffect>() {

  private var dayState = DayState()

  private var availableTags: List<MoodTagState> = emptyList()
  private var archivedTags: List<MoodTagState> = emptyList()
  private lateinit var activeMoodPalette: MoodPalette

  init {
    viewModelScope.launch {
      availableTags = getMoodTagsUseCase().map {
        MoodTagState(
          moodTag = it,
          isSelected = false,
        )
      }
      activeMoodPalette = getActiveMoodPaletteUseCase()

      loadExistingState()
    }
  }

  private suspend fun loadExistingState() {
    val existingSummary = daySummaryRepository.getDaySummaryFor(getDateUseCase.getEpochSeconds())
    val state = if (existingSummary != null) {
      dayState = dayState.copy(
        color = existingSummary.color,
        bestPart = existingSummary.bestPart,
        selectedTags = existingSummary.tags.map {
          MoodTagState(
            moodTag = it,
            isSelected = true,
          )
        },
      )
      val selectedTagIds = dayState.selectedTags.map { it.moodTag.moodTagId }
      availableTags = availableTags.map {
        it.copy(isSelected = it.moodTag.moodTagId in selectedTagIds)
      }
      TodayScreenState.Summarize(
        backgroundColor = dayState.color,
        date = getDateUseCase.getDateString(),
        bestPart = dayState.bestPart,
        selectedTags = dayState.selectedTags,
      )
    } else {
      TodayScreenState.SelectColor(
        colorPalette = activeMoodPalette.colors,
        selectedColor = null,
      )
    }
    setState { state }
  }

  override fun createInitialState(): TodayScreenState = TodayScreenState.Loading

  override suspend fun handleEvent(event: TodayEvent) {
    when (event) {
      is TodayEvent.ColorSelected -> handleColorSelected(selectedColor = event.selectedColor)
      is TodayEvent.BestPartUpdated -> handleBestPartUpdated(bestPart = event.bestPart)
      is TodayEvent.BestPartSaved -> handleBestPartSaved()
      is TodayEvent.BackClicked -> handleBackClicked()
      is TodayEvent.FinishClicked -> handleFinishClicked()
      is TodayEvent.MoodTagSelected -> handleMoodTagSelected(moodTagState = event.moodTag)
      is TodayEvent.MoodTagsSaved -> handleMoodTagsSaved()
      is TodayEvent.ToggleArchivedTags -> handleToggleArchivedTags()
    }
  }

  private fun handleColorSelected(selectedColor: ULong) {
    dayState = dayState.copy(color = selectedColor)
    setState {
      TodayScreenState.ShareBestPart(
        backgroundColor = dayState.color,
        bestPart = dayState.bestPart,
        shouldAnimate = true,
      )
    }
  }

  private fun handleBestPartUpdated(bestPart: String) {
    dayState = dayState.copy(bestPart = bestPart)
    setSealedState<TodayScreenState.ShareBestPart> { current ->
      current.copy(bestPart = dayState.bestPart)
    }
  }

  private fun handleBestPartSaved() {
    setState {
      TodayScreenState.AddMoodTags(
        backgroundColor = dayState.color,
        possibleTags = availableTags,
        shouldShowArchived = false,
        archivedTags = emptyList(),
      )
    }
  }

  private fun handleMoodTagSelected(moodTagState: MoodTagState) {
    dayState = dayState.copy(
      selectedTags = if (moodTagState.isSelected) {
        dayState.selectedTags.minus(moodTagState)
      } else {
        dayState.selectedTags.plus(moodTagState.copy(isSelected = true))
      }
    )

    setSealedState<TodayScreenState.AddMoodTags> { current ->
      current.copy(possibleTags = refreshAvailableTagSelectedStates())
    }
  }

  private fun handleMoodTagsSaved() {
    setState {
      TodayScreenState.Summarize(
        backgroundColor = dayState.color,
        date = getDateUseCase.getDateString(),
        bestPart = dayState.bestPart,
        selectedTags = dayState.selectedTags,
      )
    }
  }

  private fun handleBackClicked() {
    if (state.value is TodayScreenState.Loading || state.value is TodayScreenState.SelectColor) {
      setEffect { TodaySideEffect.Navigation.Back }
      return
    }
    setState {
      when (this) {
        is TodayScreenState.Loading,
        is TodayScreenState.SelectColor,
        -> error("This should have already emitted a back navigation effect")

        is TodayScreenState.ShareBestPart -> TodayScreenState.SelectColor(
          colorPalette = activeMoodPalette.colors,
          selectedColor = null,
        )
        is TodayScreenState.AddMoodTags -> TodayScreenState.ShareBestPart(
          backgroundColor = dayState.color,
          bestPart = dayState.bestPart,
          shouldAnimate = false,
        )
        is TodayScreenState.Summarize -> TodayScreenState.AddMoodTags(
          backgroundColor = dayState.color,
          possibleTags = refreshAvailableTagSelectedStates(),
          shouldShowArchived = false,
          archivedTags = emptyList(),
        )
      }
    }
  }

  private fun handleFinishClicked() {
    viewModelScope.launch {
      daySummaryRepository.saveDaySummary(
        daySummary = DaySummary(
          date = getDateUseCase.getEpochSeconds(),
          color = dayState.color,
          bestPart = dayState.bestPart,
          tags = dayState.selectedTags.map { it.moodTag },
        )
      )
    }
    setEffect { TodaySideEffect.Navigation.Back }
  }

  private fun refreshAvailableTagSelectedStates(): List<MoodTagState> {
    availableTags = availableTags.map { availableTag ->
      val selectedTagIds = dayState.selectedTags.map { selectedTag -> selectedTag.moodTag.moodTagId }
      availableTag.copy(isSelected = availableTag.moodTag.moodTagId in selectedTagIds)
    }
    return availableTags
  }

  private suspend fun handleToggleArchivedTags() {
    val selectedTagIds = dayState.selectedTags.map { it.moodTag.moodTagId }
    if (archivedTags.isEmpty()) {
      archivedTags = getArchivedMoodTagsUseCase()
        .map { archivedTag ->
          MoodTagState(
            moodTag = archivedTag,
            isSelected = archivedTag.moodTagId in selectedTagIds,
          )
        }
    }
    setSealedState<TodayScreenState.AddMoodTags> { current ->
      current.copy(
        shouldShowArchived = !current.shouldShowArchived,
        archivedTags = if (current.shouldShowArchived) emptyList() else archivedTags,
      )
    }
  }

  data class DayState(
    val color: ULong = ULong.MIN_VALUE,
    val bestPart: String = "",
    val selectedTags: List<MoodTagState> = emptyList(),
  )
}
