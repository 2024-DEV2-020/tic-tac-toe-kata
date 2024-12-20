package com.anon2024dev2020.tictactoe.model

import com.anon2024dev2020.tictactoe.domain.model.Coordinate
import com.anon2024dev2020.tictactoe.domain.model.Player
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3UndoResult
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TicTacToe3x3UndoBehaviorTest {
    private lateinit var game: TicTacToe3x3

    @Before
    fun setup() {
        game = TicTacToe3x3()
    }

    @Test
    fun `undo should return NO_MOVES_TO_UNDO when no moves have been made`() {
        val result = game.undo()
        assertTrue(
            "Expected result to be a Failure",
            result is TicTacToe3x3UndoResult.Failure,
        )
        assertEquals(
            "Expected failure reason to be NO_MOVES_TO_UNDO",
            TicTacToe3x3UndoResult.TicTacToe3x3UndoError.NO_MOVES_TO_UNDO,
            (result as TicTacToe3x3UndoResult.Failure).reason,
        )
    }

    @Test
    fun `undo should return previous game state after one move`() {
        val initialState = game.copy()
        game = markCellAndAssertSuccess(game, Coordinate.of(0, 0)).updatedTicTacToe

        val undoResult = game.undo()
        assertTrue("Expected result to be a Success", undoResult is TicTacToe3x3UndoResult.Success)
        assertEquals(
            "Undo should return to initial state",
            initialState,
            (undoResult as TicTacToe3x3UndoResult.Success).updatedTicTacToe,
        )
    }

    @Test
    fun `undo should return correct game state after multiple moves`() {
        game = markCellAndAssertSuccess(game, Coordinate.of(0, 0)).updatedTicTacToe
        game = markCellAndAssertSuccess(game, Coordinate.of(1, 1)).updatedTicTacToe
        val stateBeforeLastMove = game.copy()
        game = markCellAndAssertSuccess(game, Coordinate.of(2, 2)).updatedTicTacToe

        val undoResult = game.undo()
        assertTrue("Expected result to be a Success", undoResult is TicTacToe3x3UndoResult.Success)
        assertEquals(
            "Undo should return to state before last move",
            stateBeforeLastMove,
            (undoResult as TicTacToe3x3UndoResult.Success).updatedTicTacToe,
        )
    }

    @Test
    fun `undo should return GAME_OVER when game is already won`() {
        getMovesFor(GameOverCondition.HORIZONTAL_TOP, GameScenario.X_WINS).forEach {
            game = markCellAndAssertSuccess(game, it).updatedTicTacToe
        }

        val undoResult = game.undo()
        assertTrue("Expected result to be a Failure", undoResult is TicTacToe3x3UndoResult.Failure)
        assertEquals(
            "Expected failure reason to be GAME_OVER",
            TicTacToe3x3UndoResult.TicTacToe3x3UndoError.GAME_OVER,
            (undoResult as TicTacToe3x3UndoResult.Failure).reason,
        )
    }

    @Test
    fun `undo should return GAME_OVER when game is already draw`() {
        getMovesFor(GameOverCondition.DRAW, GameScenario.DRAW_1).forEach {
            game = markCellAndAssertSuccess(game, it).updatedTicTacToe
        }

        val undoResult = game.undo()
        assertTrue("Expected result to be a Failure", undoResult is TicTacToe3x3UndoResult.Failure)
        assertEquals(
            "Expected failure reason to be GAME_OVER",
            TicTacToe3x3UndoResult.TicTacToe3x3UndoError.GAME_OVER,
            (undoResult as TicTacToe3x3UndoResult.Failure).reason,
        )
    }

    @Test
    fun `undo should alternate between players correctly`() {
        // X plays
        game = markCellAndAssertSuccess(game, Coordinate.of(0, 0)).updatedTicTacToe
        // O plays
        game = markCellAndAssertSuccess(game, Coordinate.of(1, 1)).updatedTicTacToe

        // before undo it's X's turn
        val undoResult1 = game.undo()
        assertTrue("Expected result to be a Success", undoResult1 is TicTacToe3x3UndoResult.Success)
        assertEquals(
            "Current player should be O after one undo",
            Player.O,
            (undoResult1 as TicTacToe3x3UndoResult.Success).updatedTicTacToe.currentPlayer,
        )

        game = undoResult1.updatedTicTacToe
        val undoResult2 = game.undo()
        assertTrue("Expected result to be a Success", undoResult2 is TicTacToe3x3UndoResult.Success)
        assertEquals(
            "Current player should be X after two undo's",
            Player.X,
            (undoResult2 as TicTacToe3x3UndoResult.Success).updatedTicTacToe.currentPlayer,
        )
    }

    @Test
    fun `undo should restore empty cell after undoing a mark`() {
        val coordinate = Coordinate.of(1, 1)
        game = markCellAndAssertSuccess(game, coordinate).updatedTicTacToe
        assertNotNull("Cell should be marked", game.playerAt(coordinate))

        val undoResult = game.undo()
        assertTrue("Expected result to be a Success", undoResult is TicTacToe3x3UndoResult.Success)
        assertNull(
            "Cell should be empty after undo",
            (undoResult as TicTacToe3x3UndoResult.Success).updatedTicTacToe.playerAt(coordinate),
        )
    }

    @Test
    fun `multiple undo's should correctly revert game to initial state`() {
        val initialState = game.copy()
        repeat(5) { i ->
            game = markCellAndAssertSuccess(game, Coordinate.of(i % 3, i / 3)).updatedTicTacToe
        }

        repeat(5) {
            val undoResult = game.undo()
            assertTrue(
                "Expected result to be a Success",
                undoResult is TicTacToe3x3UndoResult.Success,
            )
            game = (undoResult as TicTacToe3x3UndoResult.Success).updatedTicTacToe
        }

        assertEquals(
            "Game should revert to initial state after multiple undo's",
            initialState,
            game,
        )
    }

    @Test
    fun `undo followed by new move should create new game branch`() {
        game = markCellAndAssertSuccess(game, Coordinate.of(0, 0)).updatedTicTacToe
        game = markCellAndAssertSuccess(game, Coordinate.of(1, 1)).updatedTicTacToe
        val undoResult = game.undo()
        assertTrue("Expected result to be a Success", undoResult is TicTacToe3x3UndoResult.Success)
        game = (undoResult as TicTacToe3x3UndoResult.Success).updatedTicTacToe
        game = markCellAndAssertSuccess(game, Coordinate.of(2, 2)).updatedTicTacToe

        assertEquals(
            "New move should be reflected after undo",
            Player.O,
            game.playerAt(Coordinate.of(2, 2)),
        )
        assertNull("Undone move should not be present", game.playerAt(Coordinate.of(1, 1)))
    }
}
