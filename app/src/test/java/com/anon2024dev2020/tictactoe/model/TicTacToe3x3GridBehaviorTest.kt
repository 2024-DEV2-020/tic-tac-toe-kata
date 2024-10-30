package com.anon2024dev2020.tictactoe.model

import com.anon2024dev2020.tictactoe.domain.model.Player
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
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
                assertNull(game.getCell(row, col))
            }
        }
    }

    @Test
    fun `markCell should succeed and place X in empty cell`() {
        val result = game.markCell(0, 0)
        assertTrue(
            "Expected successful mark placement",
            result is TicTacToe3x3.TicTacToe3x3Result.Success,
        )
        assertEquals("Cell should contain X after placement", Player.X, game.getCell(0, 0))
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
    fun `getCell should return null for out of bounds coordinates`() {
        assertNull(game.getCell(-1, 0))
        assertNull(game.getCell(0, -1))
        assertNull(game.getCell(3, 0))
        assertNull(game.getCell(0, 3))
    }
}