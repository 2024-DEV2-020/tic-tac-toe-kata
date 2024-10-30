package com.anon2024dev2020.tictactoe.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<UiState, UiEvent> : ViewModel() {

    protected val mutableUiState: MutableStateFlow<UiState> by lazy {
        MutableStateFlow(
            initialState(),
        )
    }
    val uiState: StateFlow<UiState> by lazy { mutableUiState.asStateFlow() }

    abstract fun initialState(): UiState

    abstract fun onEvent(event: UiEvent)

    interface BaseUiState

    interface BaseUiEvent
}
