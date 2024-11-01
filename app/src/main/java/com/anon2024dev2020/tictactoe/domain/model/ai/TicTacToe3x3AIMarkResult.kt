package com.anon2024dev2020.tictactoe.domain.model.ai

import com.anon2024dev2020.tictactoe.domain.model.Coordinate

sealed class TicTacToe3x3AIMarkResult {
    data class Success(val coordinate: Coordinate) : TicTacToe3x3AIMarkResult()
    data class Failure(val reason: TicTacToe3x3AIMarkError) : TicTacToe3x3AIMarkResult()

    enum class TicTacToe3x3AIMarkError {
        GAME_OVER,
    }
}
