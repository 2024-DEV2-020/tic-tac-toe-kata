package com.anon2024dev2020.tictactoe.model

import com.anon2024dev2020.tictactoe.domain.model.Grid3x3
import com.anon2024dev2020.tictactoe.domain.model.Player
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3
import org.junit.Assert.assertEquals
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
        game = markCellAndAssertSuccess(game, 0, 0).updatedTicTacToe
        assertEquals(Player.O, game.currentPlayer)
    }

    // Kept for clarity and quick diagnosis of basic turn functionality (redundant)
    @Test
    fun `it should be player X's turn after player O`() {
        game = markCellAndAssertSuccess(game, 0, 0).updatedTicTacToe // X plays
        game = markCellAndAssertSuccess(game, 1, 0).updatedTicTacToe // O plays
        assertEquals(Player.X, game.currentPlayer)
    }

    @Test
    fun `should correctly alternate between players`() {
        val expectedPlayerOrder = listOf(
            Player.X, Player.O, Player.X,
            Player.O, Player.X, Player.O,
            Player.X, Player.O, Player.X,
        )

        movesForDrawWhenXStarts.forEachIndexed { index, (row, column) ->
            val result = markCellAndAssertSuccess(game, row, column)
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
        assertEquals(TicTacToe3x3.TicTacToe3x3State.InProgress, game.state)
        assertEquals(Player.X, game.currentPlayer)
    }

    @Test
    fun `currentPlayer should return the last player when game is won`() {
        TODO("complete when a simulated victory is created")
    }

    @Test
    fun `currentPlayer should return the last player when game is a draw`() {
        TODO("complete when a simulated draw is created")
    }
}
