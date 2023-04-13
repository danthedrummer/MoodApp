package org.dandowney.bestpartofmyday.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface ViewEvent
interface ViewState
interface ViewSideEffect

abstract class BaseViewModel<UiEvent : ViewEvent, UiState : ViewState, UiEffect : ViewSideEffect> :
  ViewModel() {

  private val initialState: UiState by lazy { createInitialState() }

  private val _event = MutableSharedFlow<UiEvent>()

  private val _state: MutableState<UiState> = mutableStateOf(initialState)
  val state: State<UiState> = _state

  private val _effect: Channel<UiEffect> = Channel()
  val effect: Flow<UiEffect> = _effect.receiveAsFlow()

  init {
    subscribeToEvents()
  }

  private fun subscribeToEvents() {
    viewModelScope.launch {
      _event.collect { handleEvent(it) }
    }
  }

  fun sendEvent(event: UiEvent) {
    viewModelScope.launch {
      _event.emit(event)
    }
  }

  protected fun setState(reduce: UiState.() -> UiState) {
    _state.value = _state.value.reduce()
  }

  protected inline fun <reified T: UiState> setSealedState(crossinline reduce: (T) -> UiState) {
    setState {
      (this as? T)
        ?.let(reduce)
        ?: return@setState this
    }
  }

  protected fun setEffect(builder: () -> UiEffect) {
    viewModelScope.launch { _effect.send(builder()) }
  }

  abstract fun createInitialState(): UiState

  abstract suspend fun handleEvent(event: UiEvent)

  companion object {
    const val SIDE_EFFECT_KEY = "side_effect_key"
  }
}
