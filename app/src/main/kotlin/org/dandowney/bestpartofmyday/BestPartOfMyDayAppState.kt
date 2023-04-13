package org.dandowney.bestpartofmyday

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String) {

  object Home : Screen("home")

  object MoodTags : Screen("moodTags")

  object MoodPalettes : Screen("moonPalettes")

  object Settings : Screen("settings")

  object Today : Screen("today")

  object Calendar : Screen("calendar")
}

@Composable
fun rememberAppState(
  navHostController: NavHostController = rememberNavController(),
) = remember {
  BestPartOfMyDayAppState(navHostController = navHostController)
}

class BestPartOfMyDayAppState(
  val navHostController: NavHostController,
) {

  fun navigateBack() {
    navHostController.popBackStack()
  }

  fun navigateTo(screen: Screen) {
    navHostController.navigate(route = screen.route)
  }
}