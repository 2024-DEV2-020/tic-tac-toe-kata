package com.anon2024dev2020.tictactoe.presentation.game

import com.anon2024dev2020.tictactoe.domain.model.Coordinate
import com.anon2024dev2020.tictactoe.domain.model.Player
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3MarkResult
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3State
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3UndoResult
import com.anon2024dev2020.tictactoe.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel
@Inject constructor(
    private val initialTicTacToe3x3: TicTacToe3x3,
) : BaseViewModel<GameViewModel.UiState, GameViewModel.UiEvent>() {

    override fun initialState(): UiState = UiState.Success(
        game = initialTicTacToe3x3,
        xTotalGamesWon = 0,
        oTotalGamesWon = 0,
        totalDraws = 0,
    )

    override fun onEvent(event: UiEvent) {
        (uiState.value as? UiState.Success)?.let { currentState ->
            when (event) {
                is UiEvent.CellClick -> handleCellClick(
                    currentState = currentState,
                    coordinate = event.coordinate,
                )

                is UiEvent.RestartClick ->
                    mutableUiState.value =
                        currentState.copy(game = initialTicTacToe3x3)

                is UiEvent.UndoClick -> handleUndoClick(
                    currentState = currentState,
                )
            }
        }
    }

    private fun handleUndoClick(currentState: UiState.Success) {
        when (val result = currentState.game.undo()) {
            is TicTacToe3x3UndoResult.Failure -> {
                // TODO: play failure sound
            }

            is TicTacToe3x3UndoResult.Success -> {
                // TODO: play generic undo sound
                mutableUiState.value = currentState.copy(game = result.updatedTicTacToe)
            }
        }
    }

    private fun handleCellClick(currentState: UiState.Success, coordinate: Coordinate) {
        when (val result = currentState.game.markCell(coordinate)) {
            is TicTacToe3x3MarkResult.Failure -> {
                // TODO: play failure sound
            }

            is TicTacToe3x3MarkResult.Success -> updateViewModelGameState(
                currentState = currentState,
                updatedGame = result.updatedTicTacToe,
            )
        }
    }

    private fun updateViewModelGameState(currentState: UiState.Success, updatedGame: TicTacToe3x3) {
        when (val updatedGameState = updatedGame.state) {
            is TicTacToe3x3State.Draw -> {
                // TODO: play draw sound
                mutableUiState.value = currentState.copy(
                    game = updatedGame,
                    totalDraws = currentState.totalDraws + 1,
                )
            }

            is TicTacToe3x3State.InProgress -> {
                // TODO: play mark placement sound
                mutableUiState.value = currentState.copy(
                    game = updatedGame,
                )
            }

            is TicTacToe3x3State.Victory -> {
                when (updatedGameState.winner) {
                    Player.X -> {
                        // TODO: play victory sound
                        mutableUiState.value = currentState.copy(
                            game = updatedGame,
                            xTotalGamesWon = currentState.xTotalGamesWon + 1,
                        )
                    }

                    Player.O -> {
                        // TODO: play victory sound
                        mutableUiState.value = currentState.copy(
                            game = updatedGame,
                            oTotalGamesWon = currentState.oTotalGamesWon + 1,
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent : BaseUiEvent {
        data class CellClick(val coordinate: Coordinate) : UiEvent()
        data object UndoClick : UiEvent()
        data object RestartClick : UiEvent()
    }

    sealed class UiState : BaseUiState {
        data class Success(
            val game: TicTacToe3x3,
            val xTotalGamesWon: Int,
            val oTotalGamesWon: Int,
            val totalDraws: Int,
        ) : UiState()
    }
}
