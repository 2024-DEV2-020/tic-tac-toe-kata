package com.anon2024dev2020.tictactoe.model

import com.anon2024dev2020.tictactoe.domain.model.Coordinate
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3State
import com.anon2024dev2020.tictactoe.domain.model.ai.TicTacToe3x3AI
import com.anon2024dev2020.tictactoe.domain.model.ai.TicTacToe3x3AIEasy
import com.anon2024dev2020.tictactoe.domain.model.ai.TicTacToe3x3AIMarkResult
import kotlin.random.Random
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TicTacToe3x3AIEasyTest {
    private lateinit var easyBot: TicTacToe3x3AI
    private lateinit var game: TicTacToe3x3

    @Before
    fun setup() {
        easyBot = TicTacToe3x3AIEasy()
        game = TicTacToe3x3()
    }

    @Test
    fun `Easy bot should make a valid move on an empty board`() {
        val result = easyBot.markCell(currentGameState = game)
        markCellAndAssertSuccess(
            game = game,
            coordinate = (result as TicTacToe3x3AIMarkResult.Success).coordinate,
        )
    }

    @Test
    fun `Easy bot should select an unoccupied cell`() {
        val random = Random(System.currentTimeMillis())
        repeat(100) {
            // reset
            game = TicTacToe3x3()
            game = markCellAndAssertSuccess(
                game = game,
                coordinate = Coordinate.of(
                    x = random.nextInt(3),
                    y = random.nextInt(3),
                ),
            ).updatedTicTacToe

            val result = easyBot.markCell(currentGameState = game)
            markCellAndAssertSuccess(
                game = game,
                coordinate = (result as TicTacToe3x3AIMarkResult.Success).coordinate,
            )
        }
    }

    @Test
    fun `bot should play until game completion regardless of outcome`() {
        do {
            // it's effectively two bots playing against each other
            val result = easyBot.markCell(currentGameState = game)
            game = markCellAndAssertSuccess(
                game = game,
                coordinate = (result as TicTacToe3x3AIMarkResult.Success).coordinate,
            ).updatedTicTacToe
        } while (game.state is TicTacToe3x3State.InProgress)
    }

    @Test
    fun `Easy bot should make the only available move on a nearly full board`() {
//        | O | X | O |
//        |---|---|---|
//        | O | X | X |
//        |---|---|---|
//        | X | O |   |
        val moves = setOf(
            Coordinate.of(0, 1),
            Coordinate.of(0, 0),
            Coordinate.of(1, 1),
            Coordinate.of(0, 2),
            Coordinate.of(1, 2),
            Coordinate.of(1, 0),
            Coordinate.of(2, 0),
            Coordinate.of(2, 1),
        )
        moves.forEach {
            game = markCellAndAssertSuccess(game = game, coordinate = it).updatedTicTacToe
        }
        val result = easyBot.markCell(currentGameState = game)
        markCellAndAssertSuccess(
            game = game,
            coordinate = (result as TicTacToe3x3AIMarkResult.Success).coordinate,
        ).updatedTicTacToe
        assertTrue(
            "Game should be in Draw state",
            game.state is TicTacToe3x3State.Draw,
        )
    }

    @Test
    fun `Easy bot should make different moves in successive games`() {
        val moveSet = mutableSetOf<Coordinate>()

        repeat(100) {
            game = TicTacToe3x3()
            val result = easyBot.markCell(currentGameState = game)
            moveSet.add((result as TicTacToe3x3AIMarkResult.Success).coordinate)
            markCellAndAssertSuccess(game = game, coordinate = result.coordinate)
        }

        assertTrue(
            "Easy bot should make different moves across multiple games",
            moveSet.size > 1,
        )
        assertTrue(
            "Easy bot should explore a significant portion of the board",
            // Expecting at least 5 different starting moves out of 9 possible
            moveSet.size >= 5,
        )
    }
}
