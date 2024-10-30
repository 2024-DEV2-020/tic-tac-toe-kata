package com.anon2024dev2020.tictactoe.model

import com.anon2024dev2020.tictactoe.domain.model.Player
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class TicTacToe3x3PlayerBehaviorTest {
    private lateinit var game: TicTacToe3x3

    private val movesForDrawXStarts = setOf(
        Pair(0, 1), // X
        Pair(0, 0), // O
        Pair(1, 1), // X
        Pair(0, 2), // O
        Pair(1, 2), // X
        Pair(1, 0), // O
        Pair(2, 0), // X
        Pair(2, 1), // O
        Pair(2, 2)  // X
    )

    @Before
    fun setup() {
        game = TicTacToe3x3()
    }

    /**
     * Helper function to mark a cell and update the game state.
     * This function performs a move, asserts its success, and updates the game state.
     *
     * @param row The row index of the cell to mark.
     * @param column The column index of the cell to mark.
     * @return The successful result of the move.
     */
    private fun markCellAndUpdateState(
        row: Int,
        column: Int,
    ): TicTacToe3x3.TicTacToe3x3Result.Success {
        val result = game.markCell(row, column)
        assertTrue("Expected successful move", result is TicTacToe3x3.TicTacToe3x3Result.Success)
        game = (result as TicTacToe3x3.TicTacToe3x3Result.Success).updatedTicTacToe
        return result
    }

    /**
     * Helper function to print the current state of the game
     */
    private fun printGrid3x3() {
        println("Current game state:")
        for (row in 0..2) {
            for (col in 0..2) {
                val cell = when (game.getCell(row, col)) {
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

    /**
     * Helper function to print the grid based on the moves made.
     *
     * @param moves List of moves made in the game
     * @param currentMoveIndex Index of the current move (X-based)
     */
    private fun printGameBoardFromMoves(moves: Set<Pair<Int, Int>>, currentMoveIndex: Int) {
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

    @Test
    fun `player X should start the game`() {
        assertEquals(Player.X, game.currentPlayer)
    }

    // Kept for clarity and quick diagnosis of basic turn functionality (redundant)
    @Test
    fun `it should be player O's turn after player X`() {
        markCellAndUpdateState(0, 0)
        assertEquals(Player.O, game.currentPlayer)
    }

    // Kept for clarity and quick diagnosis of basic turn functionality (redundant)
    @Test
    fun `it should be player X's turn after player O`() {
        markCellAndUpdateState(0, 0) // X plays
        markCellAndUpdateState(1, 0) // O plays
        assertEquals(Player.X, game.currentPlayer)
    }

    @Test
    fun `should correctly alternate between players`() {
        val expectedPlayerOrder = listOf(
            Player.X, Player.O, Player.X,
            Player.O, Player.X, Player.O,
            Player.X, Player.O, Player.X
        )

        movesForDrawXStarts.forEachIndexed { index, (row, column) ->
            val result = markCellAndUpdateState(row, column)
            assertEquals(
                "Move $index should be performed by ${expectedPlayerOrder[index]}",
                expectedPlayerOrder[index],
                result.actionPerformer
            )
        }
    }
}
