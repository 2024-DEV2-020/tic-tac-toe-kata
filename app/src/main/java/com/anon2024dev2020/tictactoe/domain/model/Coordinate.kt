package com.anon2024dev2020.tictactoe.domain.model

/**
 * Represents a 2D coordinate in a row-column grid.
 *
 * @property pair A [Pair] of integers representing the coordinates,
 *                where the first element is the row and the second is the column.
 */
@JvmInline
value class Coordinate(private val pair: Pair<Int, Int>) {
    val row get() = pair.first
    val column get() = pair.second

    companion object {
        fun of(row: Int, column: Int) = Coordinate(row to column)
    }
}
