package com.anon2024dev2020.tictactoe.model

import com.anon2024dev2020.tictactoe.domain.model.Coordinate
import com.anon2024dev2020.tictactoe.domain.model.Player
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3State
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TicTacToe3x3PlayerBehaviorTest {
    private lateinit var game: TicTacToe3x3

    @Before
    fun setup() {
        game = TicTacToe3x3()
    }

    @Test
    fun `player X should start the game`() {
        assertEquals(Player.X, game.currentPlayer)
    }

    // Kept for clarity and quick diagnosis of basic turn functionality (redundant)
    @Test
    fun `it should be player O's turn after player X`() {
        game = markCellAndAssertSuccess(game, Coordinate.of(0, 0)).updatedTicTacToe
        assertEquals(Player.O, game.currentPlayer)
    }

    // Kept for clarity and quick diagnosis of basic turn functionality (redundant)
    @Test
    fun `it should be player X's turn after player O`() {
        game = markCellAndAssertSuccess(game, Coordinate.of(0, 0)).updatedTicTacToe // X plays
        game = markCellAndAssertSuccess(game, Coordinate.of(1, 0)).updatedTicTacToe // O plays
        assertEquals(Player.X, game.currentPlayer)
    }

    @Test
    fun `should correctly alternate between players`() {
        val expectedPlayerOrder = listOf(
            Player.X, Player.O, Player.X,
            Player.O, Player.X, Player.O,
            Player.X, Player.O, Player.X,
        )
        getMovesFor(
            GameOverCondition.DRAW,
            GameScenario.DRAW_1,
        ).forEachIndexed { index, coordinate ->
            val result = markCellAndAssertSuccess(game, coordinate)
            game = result.updatedTicTacToe
            assertEquals(
                "Move $index should be performed by ${expectedPlayerOrder[index]}",
                expectedPlayerOrder[index],
                result.actionPerformer,
            )
        }
    }

    @Test
    fun `currentPlayer should return the current player when game is in progress`() {
        assertEquals(TicTacToe3x3State.InProgress, game.state)
        assertEquals(Player.X, game.currentPlayer)
    }

    @Test
    fun `currentPlayer should return last player that moved when game is won`() {
//        | O | X |   |
//        |---|---|---|
//        | O |   | X |
//        |---|---|---|
//        | O | X |   |
        getMovesFor(
            condition = GameOverCondition.VERTICAL_LEFT,
            key = GameScenario.O_WINS,
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
        assertEquals(
            "Current player should be O",
            Player.O,
            game.currentPlayer,
        )
    }

    @Test
    fun `currentPlayer should return last player that moved when game is draw`() {
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
        assertEquals(
            "Current player should be X",
            Player.X,
            game.currentPlayer,
        )
    }
}
