package com.anon2024dev2020.tictactoe.domain.model

sealed class TicTacToe3x3MarkResult {
    data class Success(val actionPerformer: Player, val updatedTicTacToe: TicTacToe3x3) :
        TicTacToe3x3MarkResult()

    data class Failure(val reason: TicTacToe3x3Error) : TicTacToe3x3MarkResult()

    enum class TicTacToe3x3Error {
        OCCUPIED_CELL,
        OUT_OF_BOUNDS,
        GAME_OVER,
    }
}
