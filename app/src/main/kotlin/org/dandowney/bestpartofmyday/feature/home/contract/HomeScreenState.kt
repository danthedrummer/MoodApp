package org.dandowney.bestpartofmyday.feature.home.contract

import androidx.annotation.DrawableRes
import org.dandowney.bestpartofmyday.Screen
import org.dandowney.bestpartofmyday.base.ViewState

data class HomeScreenState(
  val buttons: List<HomeButtonState>,
  val activeColorPalette: List<ULong>,
) : ViewState

data class HomeButtonState(
  val title: String,
  @DrawableRes val iconRes: Int,
  val screen: Screen,
)
