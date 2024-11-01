package com.anon2024dev2020.tictactoe.model

import com.anon2024dev2020.tictactoe.domain.model.Coordinate
import com.anon2024dev2020.tictactoe.domain.model.Player
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3MarkResult
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3State
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
                assertNull(game.getPlayerAt(Coordinate.of(row, col)))
            }
        }
    }

    @Test
    fun `markCell should succeed and place X in empty cell`() {
        game =
            markCellAndAssertSuccess(game = game, coordinate = Coordinate.of(0, 0)).updatedTicTacToe
        assertEquals(
            "Cell should contain X after placement",
            Player.X,
            game.getPlayerAt(
                Coordinate.of(0, 0),
            ),
        )
    }

    @Test
    fun `markCell should succeed and place O in empty cell`() {
        // X Plays
        game =
            markCellAndAssertSuccess(game = game, coordinate = Coordinate.of(0, 0)).updatedTicTacToe
        // Y Plays
        game = markCellAndAssertSuccess(game = game, Coordinate.of(1, 0)).updatedTicTacToe
        assertEquals(
            "Cell should contain O after placement",
            Player.O,
            game.getPlayerAt(
                Coordinate.of(1, 0),
            ),
        )
    }

    @Test
    fun `markCell should fail with OCCUPIED_CELL error when placing in non-empty cell`() {
        game = markCellAndAssertSuccess(game, Coordinate.of(0, 0)).updatedTicTacToe
        val result = game.markCell(Coordinate.of(0, 0))
        assertTrue(
            "Expected result to be a Failure",
            result is TicTacToe3x3MarkResult.Failure,
        )
        assertEquals(
            "Expected failure reason to be OCCUPIED_CELL",
            TicTacToe3x3MarkResult.TicTacToe3x3Error.OCCUPIED_CELL,
            (result as TicTacToe3x3MarkResult.Failure).reason,
        )
    }

    @Test
    fun `markCell should fail with OUT_OF_BOUNDS error when placing outside grid`() {
        val result = game.markCell(Coordinate.of(3, 0))
        assertTrue(
            "Expected result to be a Failure",
            result is TicTacToe3x3MarkResult.Failure,
        )
        assertEquals(
            "Expected failure reason to be OUT_OF_BOUNDS",
            TicTacToe3x3MarkResult.TicTacToe3x3Error.OUT_OF_BOUNDS,
            (result as TicTacToe3x3MarkResult.Failure).reason,
        )
    }

    @Test
    fun `markCell should gracefully fail with OUT_OF_BOUNDS error with negative indices`() {
        val result = game.markCell(Coordinate.of(-1, 0))
        assertTrue(
            "Expected result to be a Failure",
            result is TicTacToe3x3MarkResult.Failure,
        )
        assertEquals(
            "Expected failure reason to be OUT_OF_BOUNDS",
            TicTacToe3x3MarkResult.TicTacToe3x3Error.OUT_OF_BOUNDS,
            (result as TicTacToe3x3MarkResult.Failure).reason,
        )
    }

    @Test
    fun `getPlayerAtCell should throw IllegalArgumentException for out of bounds coordinates`() {
        val outOfBoundsCoordinates = listOf(
            Coordinate.of(-1, 0),
            Coordinate.of(0, -1),
            Coordinate.of(3, 0),
            Coordinate.of(0, 3),
        )

        outOfBoundsCoordinates.forEach { coordinate ->
            assertThrows(IllegalArgumentException::class.java) {
                game.getPlayerAt(coordinate)
            }
        }
    }

    @Test
    fun `markCell should return GAME_OVER error when game is already won`() {
        // | X | X | X |
        // |---|---|---|
        // | O |   |   |
        // |---|---|---|
        // |   |   | O |
        getMovesFor(GameOverCondition.HORIZONTAL_TOP, GameScenario.X_WINS).forEach {
            game = markCellAndAssertSuccess(
                game = game,
                coordinate = it,
            ).updatedTicTacToe
        }
        assertTrue(
            "Game should return Victory state",
            game.state is TicTacToe3x3State.Victory,
        )
        // try mark cell on won game
        val result = game.markCell(Coordinate.of(2, 0))
        assertTrue(
            "Expected result to be a Failure",
            result is TicTacToe3x3MarkResult.Failure,
        )
        assertEquals(
            "Expected failure reason to be GAME_OVER",
            TicTacToe3x3MarkResult.TicTacToe3x3Error.GAME_OVER,
            (result as TicTacToe3x3MarkResult.Failure).reason,
        )
    }

    @Test
    fun `markCell should return GAME_OVER error when game is already draw`() {
//        | O | X | O |
//        |---|---|---|
//        | O | X | X |
//        |---|---|---|
//        | X | O | X |
        getMovesFor(GameOverCondition.DRAW, GameScenario.DRAW_1).forEach {
            game = markCellAndAssertSuccess(
                game = game,
                coordinate = it,
            ).updatedTicTacToe
        }
        assertTrue(
            "Game should be in Draw state",
            game.state is TicTacToe3x3State.Draw,
        )
        // try mark cell on draw game
        val result = game.markCell(Coordinate.of(0, 0))
        assertTrue(
            "Expected result to be a Failure",
            result is TicTacToe3x3MarkResult.Failure,
        )
        assertEquals(
            "Expected failure reason to be GAME_OVER",
            TicTacToe3x3MarkResult.TicTacToe3x3Error.GAME_OVER,
            (result as TicTacToe3x3MarkResult.Failure).reason,
        )
    }

    @Test
    fun `markCell should not change game state when game is already draw`() {
//        | O | X | O |
//        |---|---|---|
//        | O | X | X |
//        |---|---|---|
//        | X | O | X |
        getMovesFor(GameOverCondition.DRAW, GameScenario.DRAW_1).forEach {
            game = markCellAndAssertSuccess(
                game = game,
                coordinate = it,
            ).updatedTicTacToe
        }
        assertTrue(
            "Game should be in Draw state",
            game.state is TicTacToe3x3State.Draw,
        )
        // X plays, try mark cell on draw game
        val result = game.markCell(Coordinate.of(0, 0))
        assertTrue(
            "Expected result to be a Failure",
            result is TicTacToe3x3MarkResult.Failure,
        )
        // Verify that the cell content hasn't changed
        assertEquals(
            "Cell content should not change after attempting to mark a cell in a drawn game",
            Player.O,
            game.getPlayerAt(Coordinate.of(0, 0)),
        )
    }

    @Test
    fun `markCell should not change game state when game is already won`() {
        // | X | X | X |
        // |---|---|---|
        // | O |   |   |
        // |---|---|---|
        // |   |   | O |
        getMovesFor(GameOverCondition.HORIZONTAL_TOP, GameScenario.X_WINS).forEach {
            game = markCellAndAssertSuccess(
                game = game,
                coordinate = it,
            ).updatedTicTacToe
        }
        assertTrue(
            "Game should be in Victory state",
            game.state is TicTacToe3x3State.Victory,
        )

        // try mark cell on won game
        val result = game.markCell(Coordinate.of(2, 0))
        assertTrue(
            "Expected result to be a Failure",
            result is TicTacToe3x3MarkResult.Failure,
        )
        // Verify that the cell content hasn't changed
        assertNull(
            "Cell content should not change after attempting to mark a cell in a won game",
            game.getPlayerAt(Coordinate.of(2, 0)),
        )
        // Verify that the game state is still Victory
        assertTrue(
            "Game should still be in Victory state",
            game.state is TicTacToe3x3State.Victory,
        )
    }
}
