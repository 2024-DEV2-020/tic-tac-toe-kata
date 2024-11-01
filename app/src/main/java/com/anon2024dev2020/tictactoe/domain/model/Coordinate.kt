package com.anon2024dev2020.tictactoe.domain.model

/**
 * Represents a 2D coordinate in a x y axis.
 *
 * @property pair A [Pair] of integers representing the coordinates,
 *                where the first element is x and the second is y.
 */
@JvmInline
value class Coordinate(private val pair: Pair<Int, Int>) {
    val x get() = pair.first
    val y get() = pair.second

    companion object {
        fun of(x: Int, y: Int) = Coordinate(x to y)
    }
}
