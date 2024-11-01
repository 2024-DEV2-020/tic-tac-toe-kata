package com.anon2024dev2020.tictactoe.model

import com.anon2024dev2020.tictactoe.domain.model.Coordinate
import com.anon2024dev2020.tictactoe.domain.model.Player
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3State
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TicTacToe3x3GameOverConditionsTest {
    private lateinit var game: TicTacToe3x3

    @Before
    fun setup() {
        game = TicTacToe3x3()
    }

    @Test
    fun `should detect horizontal win for X - top row`() {
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
        assertEquals(
            "Winner should be X",
            Player.X,
            (game.state as TicTacToe3x3State.Victory).winner,
        )
    }

    @Test
    fun `should detect horizontal win for O - top row`() {
        // | O | O | O |
        // |---|---|---|
        // | X |   |   |
        // |---|---|---|
        // |   | X | X |
        getMovesFor(GameOverCondition.HORIZONTAL_TOP, GameScenario.O_WINS).forEach {
            game = markCellAndAssertSuccess(
                game = game,
                coordinate = it,
            ).updatedTicTacToe
        }
        assertTrue(
            "Game should return Victory state",
            game.state is TicTacToe3x3State.Victory,
        )
        assertEquals(
            "Winner should be O",
            Player.O,
            (game.state as TicTacToe3x3State.Victory).winner,
        )
    }

    @Test
    fun `should detect horizontal win for X - middle row`() {
        // |   | O | X |
        // |---|---|---|
        // | X | X | X |
        // |---|---|---|
        // | O |   | O |
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
        assertEquals(
            "Winner should be X",
            Player.X,
            (game.state as TicTacToe3x3State.Victory).winner,
        )
    }

    @Test
    fun `should detect horizontal win for O - middle row`() {
        // |   | X |   |
        // |---|---|---|
        // | O | O | O |
        // |---|---|---|
        // | X |   | X |
        getMovesFor(GameOverCondition.HORIZONTAL_TOP, GameScenario.O_WINS).forEach {
            game = markCellAndAssertSuccess(
                game = game,
                coordinate = it,
            ).updatedTicTacToe
        }
        assertTrue(
            "Game should return Victory state",
            game.state is TicTacToe3x3State.Victory,
        )
        assertEquals(
            "Winner should be O",
            Player.O,
            (game.state as TicTacToe3x3State.Victory).winner,
        )
    }

    @Test
    fun `should detect horizontal win for X - bottom row`() {
        // |   | O | X |
        // |---|---|---|
        // | O |   | O |
        // |---|---|---|
        // | X | X | X |
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
        assertEquals(
            "Winner should be X",
            Player.X,
            (game.state as TicTacToe3x3State.Victory).winner,
        )
    }

    @Test
    fun `should detect horizontal win for O - bottom row`() {
        // |   | X |   |
        // |---|---|---|
        // | X |   | X |
        // |---|---|---|
        // | O | O | O |
        getMovesFor(GameOverCondition.HORIZONTAL_TOP, GameScenario.O_WINS).forEach {
            game = markCellAndAssertSuccess(
                game = game,
                coordinate = it,
            ).updatedTicTacToe
        }
        assertTrue(
            "Game should return Victory state",
            game.state is TicTacToe3x3State.Victory,
        )
        assertEquals(
            "Winner should be O",
            Player.O,
            (game.state as TicTacToe3x3State.Victory).winner,
        )
    }

    @Test
    fun `should detect vertical win for X - left column`() {
        // | X | O | X |
        // |---|---|---|
        // | X |   | O |
        // |---|---|---|
        // | X | O |   |
        getMovesFor(GameOverCondition.VERTICAL_LEFT, GameScenario.X_WINS).forEach {
            game = markCellAndAssertSuccess(
                game = game,
                coordinate = it,
            ).updatedTicTacToe
        }
        assertTrue(
            "Game should return Victory state",
            game.state is TicTacToe3x3State.Victory,
        )
        assertEquals(
            "Winner should be X",
            Player.X,
            (game.state as TicTacToe3x3State.Victory).winner,
        )
    }

    @Test
    fun `should detect vertical win for O - left column`() {
        // | O | X |   |
        // |---|---|---|
        // | O |   | X |
        // |---|---|---|
        // | O | X |   |
        getMovesFor(GameOverCondition.VERTICAL_LEFT, GameScenario.O_WINS).forEach {
            game = markCellAndAssertSuccess(
                game = game,
                coordinate = it,
            ).updatedTicTacToe
        }
        assertTrue(
            "Game should return Victory state",
            game.state is TicTacToe3x3State.Victory,
        )
        assertEquals(
            "Winner should be O",
            Player.O,
            (game.state as TicTacToe3x3State.Victory).winner,
        )
    }

    @Test
    fun `should detect vertical win for X - middle column`() {
        // | O | X |   |
        // |---|---|---|
        // |   | X | O |
        // |---|---|---|
        // | O | X | X |
        getMovesFor(GameOverCondition.VERTICAL_MIDDLE, GameScenario.X_WINS).forEach {
            game = markCellAndAssertSuccess(
                game = game,
                coordinate = it,
            ).updatedTicTacToe
        }
        assertTrue(
            "Game should return Victory state",
            game.state is TicTacToe3x3State.Victory,
        )
        assertEquals(
            "Winner should be X",
            Player.X,
            (game.state as TicTacToe3x3State.Victory).winner,
        )
    }

    @Test
    fun `should detect vertical win for O - middle column`() {
        // | X | O |   |
        // |---|---|---|
        // |   | O | X |
        // |---|---|---|
        // | X | O |   |
        getMovesFor(GameOverCondition.VERTICAL_MIDDLE, GameScenario.O_WINS).forEach {
            game = markCellAndAssertSuccess(
                game = game,
                coordinate = it,
            ).updatedTicTacToe
        }
        assertTrue(
            "Game should return Victory state",
            game.state is TicTacToe3x3State.Victory,
        )
        assertEquals(
            "Winner should be O",
            Player.O,
            (game.state as TicTacToe3x3State.Victory).winner,
        )
    }

    @Test
    fun `should detect vertical win for X - right column`() {
        // | O |   | X |
        // |---|---|---|
        // |   | O | X |
        // |---|---|---|
        // | O | X | X |
        getMovesFor(GameOverCondition.VERTICAL_RIGHT, GameScenario.X_WINS).forEach {
            game = markCellAndAssertSuccess(
                game = game,
                coordinate = it,
            ).updatedTicTacToe
        }
        assertTrue(
            "Game should return Victory state",
            game.state is TicTacToe3x3State.Victory,
        )
        assertEquals(
            "Winner should be X",
            Player.X,
            (game.state as TicTacToe3x3State.Victory).winner,
        )
    }

    @Test
    fun `should detect vertical win for O - right column`() {
        // | X |   | O |
        // |---|---|---|
        // |   | X | O |
        // |---|---|---|
        // | X |   | O |
        getMovesFor(GameOverCondition.VERTICAL_RIGHT, GameScenario.O_WINS).forEach {
            game = markCellAndAssertSuccess(
                game = game,
                coordinate = it,
            ).updatedTicTacToe
        }
        assertTrue(
            "Game should return Victory state",
            game.state is TicTacToe3x3State.Victory,
        )
        assertEquals(
            "Winner should be O",
            Player.O,
            (game.state as TicTacToe3x3State.Victory).winner,
        )
    }

    @Test
    fun `should detect diagonal win for X - top left to bottom right`() {
        // | X | O |   |
        // |---|---|---|
        // |   | X |   |
        // |---|---|---|
        // | O |   | X |
        getMovesFor(
            GameOverCondition.DIAGONAL_TOP_LEFT_TO_BOTTOM_RIGHT,
            GameScenario.X_WINS,
        ).forEach {
            game = markCellAndAssertSuccess(
                game = game,
                coordinate = it,
            ).updatedTicTacToe
        }
        assertTrue(
            "Game should return Victory state",
            game.state is TicTacToe3x3State.Victory,
        )
        assertEquals(
            "Winner should be X",
            Player.X,
            (game.state as TicTacToe3x3State.Victory).winner,
        )
    }

    @Test
    fun `should detect diagonal win for O - top left to bottom right`() {
        // | O | X |   |
        // |---|---|---|
        // | X | O |   |
        // |---|---|---|
        // |   | X | O |
        getMovesFor(
            GameOverCondition.DIAGONAL_TOP_LEFT_TO_BOTTOM_RIGHT,
            GameScenario.O_WINS,
        ).forEach {
            game = markCellAndAssertSuccess(
                game = game,
                coordinate = it,
            ).updatedTicTacToe
        }
        assertTrue(
            "Game should return Victory state",
            game.state is TicTacToe3x3State.Victory,
        )
        assertEquals(
            "Winner should be O",
            Player.O,
            (game.state as TicTacToe3x3State.Victory).winner,
        )
    }

    @Test
    fun `should detect diagonal win for X - top right to bottom left`() {
        // |   | O | X |
        // |---|---|---|
        // |   | X | O |
        // |---|---|---|
        // | X |   |   |
        getMovesFor(
            GameOverCondition.DIAGONAL_TOP_RIGHT_TO_BOTTOM_LEFT,
            GameScenario.X_WINS,
        ).forEach {
            game = markCellAndAssertSuccess(
                game = game,
                coordinate = it,
            ).updatedTicTacToe
        }
        assertTrue(
            "Game should return Victory state",
            game.state is TicTacToe3x3State.Victory,
        )
        assertEquals(
            "Winner should be X",
            Player.X,
            (game.state as TicTacToe3x3State.Victory).winner,
        )
    }

    @Test
    fun `should detect diagonal win for O - top right to bottom left`() {
        // | X |   | O |
        // |---|---|---|
        // | X | O |   |
        // |---|---|---|
        // | O |   | X |
        getMovesFor(
            GameOverCondition.DIAGONAL_TOP_RIGHT_TO_BOTTOM_LEFT,
            GameScenario.O_WINS,
        ).forEach {
            game = markCellAndAssertSuccess(
                game = game,
                coordinate = it,
            ).updatedTicTacToe
        }
        assertTrue(
            "Game should return Victory state",
            game.state is TicTacToe3x3State.Victory,
        )
        assertEquals(
            "Winner should be O",
            Player.O,
            (game.state as TicTacToe3x3State.Victory).winner,
        )
    }

    @Test
    fun `should be in progress if horizontal line is incomplete`() {
        // X | X |   |
        // ---|---|---|
        // O | O |   |
        // ---|---|---|
        //   |   |   |
        val moves = listOf(
            Coordinate.of(0, 0),
            Coordinate.of(1, 0),
            Coordinate.of(0, 1),
            Coordinate.of(1, 1),

        )
        moves.forEach {
            game = markCellAndAssertSuccess(
                game = game,
                coordinate = it,
            ).updatedTicTacToe
        }
        assertTrue(
            "Game should be in progress",
            game.state is TicTacToe3x3State.InProgress,
        )
    }

    @Test
    fun `should be in progress if diagonal line is incomplete`() {
        // X | O |   |
        // ---|---|---|
        //   | X |   |
        // ---|---|---|
        //   |   |   |
        val moves = listOf(
            Coordinate.of(0, 0),
            Coordinate.of(0, 1),
            Coordinate.of(1, 1),
        )
        moves.forEach {
            game = markCellAndAssertSuccess(
                game = game,
                coordinate = it,
            ).updatedTicTacToe
        }
        assertTrue(
            "Game should be in progress",
            game.state is TicTacToe3x3State.InProgress,
        )
    }

    @Test
    fun `should be in progress if vertical line is incomplete`() {
        // X | O |   |
        // ---|---|---|
        // X |   |   |
        // ---|---|---|
        //   |   | O |
        val moves = listOf(
            Coordinate.of(0, 0),
            Coordinate.of(0, 1),
            Coordinate.of(1, 0),
            Coordinate.of(2, 2),
        )
        moves.forEach {
            game = markCellAndAssertSuccess(
                game = game,
                coordinate = it,
            ).updatedTicTacToe
        }
        assertTrue(
            "Game should be in progress",
            game.state is TicTacToe3x3State.InProgress,
        )
    }

    @Test
    fun `should not detect win with opponent pieces in line`() {
        // X | O | X |
        // ---|---|---|
        //   |   |   |
        // ---|---|---|
        //   |   |   |
        val moves = listOf(
            Coordinate.of(0, 0),
            Coordinate.of(0, 1),
            Coordinate.of(0, 2),
        )
        moves.forEach {
            game = markCellAndAssertSuccess(
                game = game,
                coordinate = it,
            ).updatedTicTacToe
        }
        assertTrue(
            "Game should be in progress",
            game.state is TicTacToe3x3State.InProgress,
        )
    }

    @Test
    fun `should detect draw when all cells are full and there is no winner`() {
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
    }

    @Test
    fun `should detect win when all cells are full and there is a winner`() {
//        | O | X | X |
//        |---|---|---|
//        | O | O | X |
//        |---|---|---|
//        | X | O | X |
        getMovesFor(
            GameOverCondition.VERTICAL_RIGHT,
            GameScenario.X_WINS_AND_BOARD_IS_FULL,
        ).forEach {
            game = markCellAndAssertSuccess(
                game = game,
                coordinate = it,
            ).updatedTicTacToe
        }
        assertTrue(
            "Game should be in Victory state",
            game.state is TicTacToe3x3State.Victory,
        )
    }
}
