package com.anon2024dev2020.tictactoe.model

import com.anon2024dev2020.tictactoe.domain.model.Player
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TicTacToe3x3GridBehaviorTest {
    private lateinit var game: TicTacToe3x3

    @Before
    fun setup() {
        game = TicTacToe3x3()
    }

    @Test
    fun `new grid should be empty`() {
        for (row in 0..2) {
            for (col in 0..2) {
                assertNull(game.getPlayerAtCell(row, col))
            }
        }
    }

    @Test
    fun `markCell should succeed and place X in empty cell`() {
        game = markCellAndAssertSuccess(game = game, row = 0, column = 0).updatedTicTacToe
        assertEquals(
            "Cell should contain X after placement",
            Player.X,
            game.getPlayerAtCell(
                0,
                0,
            ),
        )
    }

    @Test
    fun `markCell should succeed and place O in empty cell`() {
        // X Plays
        game = markCellAndAssertSuccess(game = game, row = 0, column = 0).updatedTicTacToe
        // Y Plays
        game = markCellAndAssertSuccess(game = game, row = 1, column = 0).updatedTicTacToe
        assertEquals(
            "Cell should contain O after placement",
            Player.O,
            game.getPlayerAtCell(
                1,
                0,
            ),
        )
    }

    @Test
    fun `markCell should fail with OCCUPIED_CELL error when placing in non-empty cell`() {
        game = markCellAndAssertSuccess(game, 0, 0).updatedTicTacToe
        val result = game.markCell(0, 0)
        assertTrue(
            "Expected result to be a Failure",
            result is TicTacToe3x3.TicTacToe3x3Result.Failure,
        )
        assertEquals(
            "Expected failure reason to be OCCUPIED_CELL",
            TicTacToe3x3.TicTacToe3x3Result.TicTacToe3x3Error.OCCUPIED_CELL,
            (result as TicTacToe3x3.TicTacToe3x3Result.Failure).reason,
        )
    }

    @Test
    fun `markCell should fail with OUT_OF_BOUNDS error when placing outside grid`() {
        val result = game.markCell(3, 0)
        assertTrue(
            "Expected result to be a Failure",
            result is TicTacToe3x3.TicTacToe3x3Result.Failure,
        )
        assertEquals(
            "Expected failure reason to be OUT_OF_BOUNDS",
            TicTacToe3x3.TicTacToe3x3Result.TicTacToe3x3Error.OUT_OF_BOUNDS,
            (result as TicTacToe3x3.TicTacToe3x3Result.Failure).reason,
        )
    }

    @Test
    fun `markCell should gracefully fail with OUT_OF_BOUNDS error with negative indices`() {
        val result = game.markCell(-1, 0)
        assertTrue(
            "Expected result to be a Failure",
            result is TicTacToe3x3.TicTacToe3x3Result.Failure,
        )
        assertEquals(
            "Expected failure reason to be OUT_OF_BOUNDS",
            TicTacToe3x3.TicTacToe3x3Result.TicTacToe3x3Error.OUT_OF_BOUNDS,
            (result as TicTacToe3x3.TicTacToe3x3Result.Failure).reason,
        )
    }

    @Test
    fun `getPlayerAtCell should throw IllegalArgumentException for out of bounds coordinates`() {
        val outOfBoundsCoordinates = listOf(
            Pair(-1, 0),
            Pair(0, -1),
            Pair(3, 0),
            Pair(0, 3),
        )

        outOfBoundsCoordinates.forEach { (row, col) ->
            assertThrows(IllegalArgumentException::class.java) {
                game.getPlayerAtCell(row, col)
            }
        }
    }
}