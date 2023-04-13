package org.dandowney.bestpartofmyday.feature.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.dandowney.bestpartofmyday.R
import org.dandowney.bestpartofmyday.Screen
import org.dandowney.bestpartofmyday.base.BaseViewModel
import org.dandowney.bestpartofmyday.data.usecase.CollectActiveMoodPaletteUseCase
import org.dandowney.bestpartofmyday.feature.home.contract.HomeButtonState
import org.dandowney.bestpartofmyday.feature.home.contract.HomeEvent
import org.dandowney.bestpartofmyday.feature.home.contract.HomeScreenState
import org.dandowney.bestpartofmyday.feature.home.contract.HomeSideEffect
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val collectActiveMoodPaletteUseCase: CollectActiveMoodPaletteUseCase,
) : BaseViewModel<HomeEvent, HomeScreenState, HomeSideEffect>() {

  override fun createInitialState(): HomeScreenState = HomeScreenState(
    buttons = listOf(
      HomeButtonState(
        title = "Today",
        iconRes = R.drawable.ic_create,
        screen = Screen.Today,
      ),
      HomeButtonState(
        title = "Calendar",
        iconRes = R.drawable.ic_calendar,
        screen = Screen.Calendar,
      ),
      HomeButtonState(
        title = "Tags",
        iconRes = R.drawable.ic_tag,
        screen = Screen.MoodTags,
      ),
      HomeButtonState(
        title = "Palettes",
        iconRes = R.drawable.ic_palette,
        screen = Screen.MoodPalettes,
      ),
      // TODO: Leaving out settings for now until I have a compelling reason to add them
//      HomeButtonState(
//        title =  "Settings",
//        iconRes = R.drawable.ic_settings,
//        screen = Screen.Settings,
//      ),
    ),
    activeColorPalette = emptyList(),
  )

  override suspend fun handleEvent(event: HomeEvent) {
    when (event) {
      HomeEvent.Initialize -> initialize()
    }
  }

  private suspend fun initialize() {
    viewModelScope.launch {
      collectActiveMoodPaletteUseCase().collect { updatedPalette ->
        setState {
          copy(activeColorPalette = updatedPalette.colors)
        }
      }
    }
  }
}
