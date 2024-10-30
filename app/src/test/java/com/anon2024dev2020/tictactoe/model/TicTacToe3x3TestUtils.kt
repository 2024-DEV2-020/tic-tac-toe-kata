package com.anon2024dev2020.tictactoe.model

import com.anon2024dev2020.tictactoe.domain.model.Player
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3
import org.junit.Assert.assertTrue

internal val movesForDrawWhenXStarts = setOf(
    // X
    Pair(0, 1),
    // O
    Pair(0, 0),
    // X
    Pair(1, 1),
    // O
    Pair(0, 2),
    // X
    Pair(1, 2),
    // O
    Pair(1, 0),
    // X
    Pair(2, 0),
    // O
    Pair(2, 1),
    // X
    Pair(2, 2),
)

/**
 * Marks a cell in the game and asserts that the move was successful.
 *
 * @param game The TicTacToe3x3 game instance.
 * @param row The row index of the cell to mark (0-2).
 * @param column The column index of the cell to mark (0-2).
 * @return The successful result of the move.
 * @throws AssertionError if the move is not successful.
 */
internal fun markCellAndAssertSuccess(
    game: TicTacToe3x3,
    row: Int,
    column: Int,
): TicTacToe3x3.TicTacToe3x3Result.Success {
    val result = game.markCell(row, column)
    assertTrue("Expected successful move", result is TicTacToe3x3.TicTacToe3x3Result.Success)
    return result as TicTacToe3x3.TicTacToe3x3Result.Success
}

/**
 * Helper function to print the grid based on the moves made.
 *
 * @param moves List of moves made in the game
 * @param currentMoveIndex Index of the current move (X-based)
 */
internal fun printGameBoardFromMoves(moves: Set<Pair<Int, Int>>, currentMoveIndex: Int) {
    println("Game state after move $currentMoveIndex:")
    for (row in 0..2) {
        for (col in 0..2) {
            val moveIndex = moves.indexOfFirst { it == Pair(row, col) }
            val cell = when {
                moveIndex == -1 -> " "
                moveIndex <= currentMoveIndex -> if (moveIndex % 2 == 0) "X" else "O"
                else -> " "
            }
            print("| $cell ")
        }
        println("|")
        if (row < 2) println("|---|---|---|")
    }
    println("\n")
}

/**
 * Helper function to print the current state of the game
 */
internal fun printGrid3x3(game: TicTacToe3x3) {
    println("Current game state:")
    for (row in 0..2) {
        for (col in 0..2) {
            val cell = when (game.getPlayerAtCell(row, col)) {
                Player.X -> "X"
                Player.O -> "O"
                null -> " "
            }
            print("| $cell ")
        }
        println("|")
        if (row < 2) println("|---|---|---|")
    }
    println("\n")
}
