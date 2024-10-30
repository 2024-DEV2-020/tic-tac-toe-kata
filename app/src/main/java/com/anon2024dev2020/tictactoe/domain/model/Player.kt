package com.anon2024dev2020.tictactoe.domain.model

enum class Player {
    X,
    O,
}

val Player.opponent: Player
    get() = when (this) {
        Player.X -> Player.O
        Player.O -> Player.X
    }
