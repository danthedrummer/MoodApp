package org.dandowney.bestpartofmyday.design.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BestThemeViewModel @Inject constructor(
  private val themeRepository: ThemeRepository,
) : ViewModel() {

  private val _isDarkTheme = MutableStateFlow(false)
  val isDarkTheme: Flow<Boolean> = _isDarkTheme

  init {
    viewModelScope.launch {
      _isDarkTheme.value = themeRepository.isDarkTheme()
    }
  }
}