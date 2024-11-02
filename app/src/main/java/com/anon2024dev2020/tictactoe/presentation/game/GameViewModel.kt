package com.anon2024dev2020.tictactoe.presentation.game

import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3
import com.anon2024dev2020.tictactoe.presentation.BaseViewModel
import com.anon2024dev2020.tictactoe.ui.navigation.destinations.game.Game
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
        data class Success(
            val game: TicTacToe3x3,
            val xTotalGamesWon: Int,
            val oTotalGamesWon: Int,
            val totalDraws: Int,
        ) : UiState()

        data class Error(val message: String) : UiState()
    }
}
