package com.anon2024dev2020.tictactoe.domain.model

class Grid3x3 {



    sealed class Grid3x3Result {
        data class Success(val actionPerformer: Player, val updatedGrid: Grid3x3) : Grid3x3Result()
        data class Failure(val reason: Grid3x3Error) : Grid3x3Result ()

        enum class Grid3x3Error {
            INVALID_MARK
        }
    }
}