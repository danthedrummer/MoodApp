package org.dandowney.bestpartofmyday

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.dandowney.bestpartofmyday.feature.calendar.composable.Calendar
import org.dandowney.bestpartofmyday.feature.home.composable.Home
import org.dandowney.bestpartofmyday.feature.palette.composable.MoodPalettes
import org.dandowney.bestpartofmyday.feature.tags.composable.MoodTagsScreen
import org.dandowney.bestpartofmyday.feature.today.composable.Today

@Composable
fun BestPartOfMyDayApp(
  appState: BestPartOfMyDayAppState = rememberAppState(),
) {
  NavHost(
    navController = appState.navHostController,
    startDestination = Screen.Home.route,
  ) {

    composable(Screen.Home.route) {
      Home(
        onHomeButtonClicked = appState::navigateTo,
      )
    }

    composable(Screen.Today.route) {
      Today(
        onNavigateBack = appState::navigateBack,
      )
    }

    composable(Screen.Calendar.route) {
      Calendar(
        onNavigateBack = appState::navigateBack,
      )
    }

    composable(Screen.MoodTags.route) {
      MoodTagsScreen(
        onNavigateBack = appState::navigateBack,
      )
    }

    composable(Screen.MoodPalettes.route) {
      MoodPalettes(
        onNavigateBack = appState::navigateBack,
      )
    }

    composable(Screen.Settings.route) {
      // TODO: Settings screen
      if (it.getLifecycle().currentState.isAtLeast(Lifecycle.State.RESUMED)) {
        Toast.makeText(LocalContext.current, "Settings not implemented yet", Toast.LENGTH_SHORT)
          .show()
        appState.navigateTo(Screen.Home)
      }
    }
  }
}
