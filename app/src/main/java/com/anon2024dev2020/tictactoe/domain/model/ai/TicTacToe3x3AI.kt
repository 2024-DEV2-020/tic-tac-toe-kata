package com.anon2024dev2020.tictactoe.domain.model.ai

import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3

abstract class TicTacToe3x3AI {
    abstract fun markCell(currentGameState: TicTacToe3x3): TicTacToe3x3AIMarkResult
}
