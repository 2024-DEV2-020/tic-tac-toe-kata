package com.anon2024dev2020.tictactoe.domain.model.ai

import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3

/**
 * A simple AI that chooses a random available coordinate
 */
// TODO: https://www.geeksforgeeks.org/finding-optimal-move-in-tic-tac-toe-using-minimax-algorithm-in-game-theory/
class TicTacToe3x3AIEasy : TicTacToe3x3AI() {
    override fun markCell(currentGameState: TicTacToe3x3): TicTacToe3x3AIMarkResult {
        val emptyCells = currentGameState.emptyCells
        return when {
            emptyCells.isEmpty() -> TicTacToe3x3AIMarkResult.Failure(
                TicTacToe3x3AIMarkResult.TicTacToe3x3AIMarkError.GAME_OVER,
            )
            else -> TicTacToe3x3AIMarkResult.Success(emptyCells.random().coordinate)
        }
    }
}
