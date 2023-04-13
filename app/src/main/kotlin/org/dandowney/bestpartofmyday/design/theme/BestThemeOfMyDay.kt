package org.dandowney.bestpartofmyday.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel

private val DarkColorPalette = darkColors(
  primary = Gray200,
  primaryVariant = Gray500,
  secondary = Gray200,
  background = Gray800,
  surface = Gray800,
  onPrimary = Gray800,
  onSecondary = Gray800,
  onBackground = Gray200,
  onSurface = Gray200,
)

private val LightColorPalette = lightColors(
  primary = Gray800,
  primaryVariant = Gray500,
  secondary = Gray500,
  background = Gray200,
  surface = Gray200,
  onPrimary = Gray200,
  onSecondary = Gray200,
  onBackground = Gray800,
  onSurface = Gray800,
)

@Composable
fun BestThemeOfMyDay(
  themeViewModel: BestThemeViewModel = hiltViewModel(),
  content: @Composable () -> Unit,
) {
  val darkTheme by themeViewModel.isDarkTheme.collectAsState(initial = isDarkTheme())
  BestThemeOfMyDay(
    darkTheme = darkTheme,
    content = content
  )
}

@Composable
fun BestThemeOfMyDay(
  darkTheme: Boolean,
  content: @Composable () -> Unit,
) {
  val colors = if (darkTheme) {
    DarkColorPalette
  } else {
    LightColorPalette
  }

  MaterialTheme(
    colors = colors,
    typography = Typography,
    shapes = Shapes,
    content = content
  )
}

@Composable
fun BestThemeOfMyDayPreview(content: @Composable () -> Unit) {
  BestThemeOfMyDay(
    darkTheme = isSystemInDarkTheme(),
    content = content,
  )
}

@Composable
@ReadOnlyComposable
fun isDarkTheme() = LocalDarkTheme.current.value

private val LocalDarkTheme = compositionLocalOf { mutableStateOf(false) }
