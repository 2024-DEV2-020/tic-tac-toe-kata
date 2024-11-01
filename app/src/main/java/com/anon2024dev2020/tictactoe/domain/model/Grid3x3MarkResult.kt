package com.anon2024dev2020.tictactoe.domain.model

sealed class Grid3x3MarkResult {
    data class Success(val updatedGrid: Grid3x3) : Grid3x3MarkResult()
    data class Failure(val reason: Grid3x3MarkError) : Grid3x3MarkResult()

    enum class Grid3x3MarkError {
        OCCUPIED_CELL,
        OUT_OF_BOUNDS,
        GAME_OVER,
    }
}

sealed class Grid3x3UndoResult {
    data class Success(val updatedGrid: Grid3x3) : Grid3x3MarkResult()
    data class Failure(val reason: Grid3x3UndoError) : Grid3x3MarkResult()

    enum class Grid3x3UndoError {
        OUT_OF_BOUNDS,
        GAME_OVER,
    }
}

sealed class TicTacToe3x3UndoResult {
    data class Success(val updatedTicTacToe: TicTacToe3x3) :
        TicTacToe3x3UndoResult()

    data class Failure(val reason: TicTacToe3x3UndoError) : TicTacToe3x3UndoResult()

    enum class TicTacToe3x3UndoError {
        OUT_OF_BOUNDS,
        GAME_OVER,
    }
}
