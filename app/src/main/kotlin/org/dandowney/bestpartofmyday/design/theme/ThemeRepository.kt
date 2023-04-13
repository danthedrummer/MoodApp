package org.dandowney.bestpartofmyday.design.theme

interface ThemeRepository {

  suspend fun isDarkTheme(): Boolean

  suspend fun setDarkTheme(isDarkTheme: Boolean)
}