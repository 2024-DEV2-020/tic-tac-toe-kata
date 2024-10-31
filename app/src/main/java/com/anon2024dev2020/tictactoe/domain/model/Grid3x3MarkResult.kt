package com.anon2024dev2020.tictactoe.domain.model

sealed class Grid3x3MarkResult {
    data class Success(val updatedGrid: Grid3x3) : Grid3x3MarkResult()
    data class Failure(val reason: Grid3x3Error) : Grid3x3MarkResult()

    enum class Grid3x3Error {
        OCCUPIED_CELL,
        OUT_OF_BOUNDS,
        GAME_OVER,
    }
}
