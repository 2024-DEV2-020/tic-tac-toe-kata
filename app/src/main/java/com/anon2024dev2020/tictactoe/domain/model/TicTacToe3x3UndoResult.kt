package com.anon2024dev2020.tictactoe.domain.model

sealed class TicTacToe3x3UndoResult {
    data class Success(val updatedTicTacToe: TicTacToe3x3) :
        TicTacToe3x3UndoResult()

    data class Failure(val reason: TicTacToe3x3UndoError) : TicTacToe3x3UndoResult()

    enum class TicTacToe3x3UndoError {
        NO_MOVES_TO_UNDO,
        GAME_OVER,
    }
}
