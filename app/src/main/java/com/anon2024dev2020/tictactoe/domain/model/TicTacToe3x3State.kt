package com.anon2024dev2020.tictactoe.domain.model

sealed class TicTacToe3x3State {
    data object InProgress : TicTacToe3x3State()
    data class Victory(val winner: Player) : TicTacToe3x3State()
    data object Draw : TicTacToe3x3State()
}
