package com.anon2024dev2020.tictactoe.presentation.game

import com.anon2024dev2020.tictactoe.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel
@Inject constructor() :
    BaseViewModel<GameViewModel.UiState, GameViewModel.UiEvent>() {

    override fun initialState(): UiState = UiState.Initial

    override fun onEvent(event: UiEvent) {
    }

    sealed class UiEvent : BaseUiEvent

    sealed class UiState : BaseUiState {
        data object Initial : UiState()
        data object Loading : UiState()
        data class Success(val screenName: String = "Game Screen") : UiState()
        data class Error(val message: String) : UiState()
    }
}
