package com.anon2024dev2020.tictactoe.model

import com.anon2024dev2020.tictactoe.domain.model.Coordinate
import com.anon2024dev2020.tictactoe.domain.model.Player
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3MarkResult
import org.junit.Assert.assertTrue

enum class GameOverCondition {
    HORIZONTAL_TOP,
    HORIZONTAL_MIDDLE,
    HORIZONTAL_BOTTOM,
    VERTICAL_LEFT,
    VERTICAL_MIDDLE,
    VERTICAL_RIGHT,
    DIAGONAL_TOP_LEFT_TO_BOTTOM_RIGHT,
    DIAGONAL_TOP_RIGHT_TO_BOTTOM_LEFT,
    DRAW,
}

enum class GameScenario {
    X_WINS,
    X_WINS_AND_BOARD_IS_FULL,
    O_WINS,
    DRAW_1,
}

data class MoveSet(
    val moves: Set<Coordinate>,
    val finalGameBoardState: String,
)

val gameMoves = mapOf(
    GameOverCondition.HORIZONTAL_TOP to mapOf(
        GameScenario.X_WINS to MoveSet(
            moves = setOf(
                Coordinate.of(0, 1),
                Coordinate.of(1, 0),
                Coordinate.of(0, 0),
                Coordinate.of(2, 2),
                Coordinate.of(0, 2),
            ),
            finalGameBoardState = """
                | X | X | X |
                |---|---|---|
                | O |   |   |
                |---|---|---|
                |   |   | O |
            """.trimIndent(),
        ),
        GameScenario.O_WINS to MoveSet(
            moves = setOf(
                Coordinate.of(1, 0),
                Coordinate.of(0, 1),
                Coordinate.of(2, 2),
                Coordinate.of(0, 0),
                Coordinate.of(2, 1),
                Coordinate.of(0, 2),
            ),
            finalGameBoardState = """
                | O | O | O |
                |---|---|---|
                | X |   |   |
                |---|---|---|
                |   | X | X |
            """.trimIndent(),
        ),
    ),
    GameOverCondition.HORIZONTAL_MIDDLE to mapOf(
        GameScenario.X_WINS to MoveSet(
            moves = setOf(
                Coordinate.of(0, 2),
                Coordinate.of(0, 1),
                Coordinate.of(1, 0),
                Coordinate.of(2, 0),
                Coordinate.of(1, 2),
                Coordinate.of(2, 2),
                Coordinate.of(1, 1),

            ),
            finalGameBoardState = """
                |   | O | X |
                |---|---|---|
                | X | X | X |
                |---|---|---|
                | O |   | O |
            """.trimIndent(),
        ),
        GameScenario.O_WINS to MoveSet(
            moves = setOf(
                Coordinate.of(0, 1),
                Coordinate.of(1, 0),
                Coordinate.of(2, 0),
                Coordinate.of(1, 2),
                Coordinate.of(2, 2),
                Coordinate.of(1, 1),
            ),
            finalGameBoardState = """
                |   | X |   |
                |---|---|---|
                | O | O | O |
                |---|---|---|
                | X |   | X |
            """.trimIndent(),
        ),
    ),
    GameOverCondition.HORIZONTAL_BOTTOM to mapOf(
        GameScenario.X_WINS to MoveSet(
            moves = setOf(
                Coordinate.of(0, 2),
                Coordinate.of(0, 1),
                Coordinate.of(2, 0),
                Coordinate.of(1, 0),
                Coordinate.of(2, 2),
                Coordinate.of(1, 2),
                Coordinate.of(2, 1),
            ),
            finalGameBoardState = """
                |   | O | X |
                |---|---|---|
                | O |   | O |
                |---|---|---|
                | X | X | X |
            """.trimIndent(),
        ),
        GameScenario.O_WINS to MoveSet(
            moves = setOf(
                Coordinate.of(0, 1),
                Coordinate.of(2, 0),
                Coordinate.of(1, 0),
                Coordinate.of(2, 2),
                Coordinate.of(1, 2),
                Coordinate.of(2, 1),

            ),
            finalGameBoardState = """
                |   | X |   |
                |---|---|---|
                | X |   | X |
                |---|---|---|
                | O | O | O |
            """.trimIndent(),
        ),
    ),
    GameOverCondition.VERTICAL_LEFT to mapOf(
        GameScenario.X_WINS to MoveSet(
            moves = setOf(
                Coordinate.of(0, 0),
                Coordinate.of(0, 1),
                Coordinate.of(0, 2),
                Coordinate.of(1, 2),
                Coordinate.of(1, 0),
                Coordinate.of(2, 1),
                Coordinate.of(2, 0),

            ),
            finalGameBoardState = """
                | X1 | O2 | X3 |
                |----|----|----|
                | X5 |    | O4 |
                |----|----|----|
                | X7 | O6 |    |
            """.trimIndent(),
        ),
        GameScenario.O_WINS to MoveSet(
            moves = setOf(
                Coordinate.of(0, 1), Coordinate.of(0, 0), Coordinate.of(1, 2),
                Coordinate.of(1, 0), Coordinate.of(2, 1), Coordinate.of(2, 0),
            ),
            finalGameBoardState = """
                | O | X |   |
                |---|---|---|
                | O |   | X |
                |---|---|---|
                | O | X |   |
            """.trimIndent(),
        ),
    ),
    GameOverCondition.VERTICAL_MIDDLE to mapOf(
        GameScenario.X_WINS to MoveSet(
            moves = setOf(
                Coordinate.of(0, 1), Coordinate.of(0, 0), Coordinate.of(1, 1),
                Coordinate.of(1, 2), Coordinate.of(2, 2), Coordinate.of(2, 0),
                Coordinate.of(2, 1),

            ),
            finalGameBoardState = """
                | O | X |   |
                |---|---|---|
                |   | X | O |
                |---|---|---|
                | O | X | X |
            """.trimIndent(),
        ),
        GameScenario.O_WINS to MoveSet(
            moves = setOf(
                Coordinate.of(0, 0),
                Coordinate.of(0, 1),
                Coordinate.of(1, 2),
                Coordinate.of(1, 1),
                Coordinate.of(2, 0),
                Coordinate.of(2, 1),

            ),
            finalGameBoardState = """
                | X | O |   |
                |---|---|---|
                |   | O | X |
                |---|---|---|
                | X | O |   |
            """.trimIndent(),
        ),
    ),
    GameOverCondition.VERTICAL_RIGHT to mapOf(
        GameScenario.X_WINS to MoveSet(
            moves = setOf(
                Coordinate.of(0, 2), Coordinate.of(0, 0), Coordinate.of(1, 2),
                Coordinate.of(1, 1), Coordinate.of(2, 1), Coordinate.of(2, 0),
                Coordinate.of(2, 2),
            ),

            finalGameBoardState = """
                | O |   | X |
                |---|---|---|
                |   | O | X |
                |---|---|---|
                | O | X | X |
            """.trimIndent(),
        ),
        GameScenario.X_WINS_AND_BOARD_IS_FULL to MoveSet(
            moves = setOf(
                Coordinate.of(0, 2),
                Coordinate.of(0, 0),
                Coordinate.of(1, 2),
                Coordinate.of(1, 1),
                Coordinate.of(0, 1),
                Coordinate.of(1, 0),
                Coordinate.of(2, 0),
                Coordinate.of(2, 1),
                Coordinate.of(2, 2),
            ),
            finalGameBoardState = """
                    | O | X | X |
                    |---|---|---|
                    | O | O | X |
                    |---|---|---|
                    | X | O | X |
            """.trimIndent(),
        ),
        GameScenario.O_WINS to MoveSet(
            moves = setOf(
                Coordinate.of(0, 0),
                Coordinate.of(0, 2),
                Coordinate.of(1, 1),
                Coordinate.of(1, 2),
                Coordinate.of(2, 0),
                Coordinate.of(2, 2),
            ),
            finalGameBoardState = """
                | X |   | O |
                |---|---|---|
                |   | X | O |
                |---|---|---|
                | X |   | O |
            """.trimIndent(),
        ),
    ),
    GameOverCondition.DIAGONAL_TOP_LEFT_TO_BOTTOM_RIGHT to mapOf(
        GameScenario.X_WINS to MoveSet(
            moves = setOf(
                Coordinate.of(0, 0),
                Coordinate.of(0, 1),
                Coordinate.of(1, 1),
                Coordinate.of(2, 0),
                Coordinate.of(2, 2),

            ),
            finalGameBoardState = """
                | X | O |   |
                |---|---|---|
                |   | X |   |
                |---|---|---|
                | O |   | X |
            """.trimIndent(),
        ),
        GameScenario.O_WINS to MoveSet(
            moves = setOf(
                Coordinate.of(0, 1),
                Coordinate.of(0, 0),
                Coordinate.of(1, 0),
                Coordinate.of(1, 1),
                Coordinate.of(2, 1),
                Coordinate.of(2, 2),

            ),
            finalGameBoardState = """
                | O | X |   |
                |---|---|---|
                | X | O |   |
                |---|---|---|
                |   | X | O |
            """.trimIndent(),
        ),
    ),
    GameOverCondition.DIAGONAL_TOP_RIGHT_TO_BOTTOM_LEFT to mapOf(
        GameScenario.X_WINS to MoveSet(
            moves = setOf(
                Coordinate.of(0, 2),
                Coordinate.of(0, 1),
                Coordinate.of(1, 1),
                Coordinate.of(1, 2),
                Coordinate.of(2, 0),
            ),
            finalGameBoardState = """
                |   | O | X |
                |---|---|---|
                |   | X | O |
                |---|---|---|
                | X |   |   |
            """.trimIndent(),
        ),
        GameScenario.O_WINS to MoveSet(
            moves = setOf(
                Coordinate.of(0, 0),
                Coordinate.of(0, 2),
                Coordinate.of(1, 0),
                Coordinate.of(1, 1),
                Coordinate.of(2, 2),
                Coordinate.of(2, 0),

            ),
            finalGameBoardState = """
                | X |   | O |
                |---|---|---|
                | X | O |   |
                |---|---|---|
                | O |   | X |
            """.trimIndent(),
        ),
    ),
    GameOverCondition.DRAW to mapOf(
        GameScenario.DRAW_1 to MoveSet(
            moves = setOf(
                Coordinate.of(0, 1),
                Coordinate.of(0, 0),
                Coordinate.of(1, 1),
                Coordinate.of(0, 2),
                Coordinate.of(1, 2),
                Coordinate.of(1, 0),
                Coordinate.of(2, 0),
                Coordinate.of(2, 1),
                Coordinate.of(2, 2),
            ),
            finalGameBoardState = """
                | O | X | O |
                |---|---|---|
                | O | X | X |
                |---|---|---|
                | X | O | X |
            """.trimIndent(),
        ),
    ),
)

fun getMovesFor(condition: GameOverCondition, key: GameScenario): Set<Coordinate> {
    return gameMoves[condition]?.get(key)?.moves ?: emptySet()
}

fun getFinalGameBoardStateFor(condition: GameOverCondition, key: GameScenario): String {
    return gameMoves[condition]?.get(key)?.finalGameBoardState ?: ""
}

/**
 * Marks a cell in the game and asserts that the move was successful.
 *
 * @param game The TicTacToe3x3 game instance.
 * @param coordinate The coordinate of the cell to mark.
 * @return The successful result of the move.
 * @throws AssertionError if the move is not successful.
 */
internal fun markCellAndAssertSuccess(
    game: TicTacToe3x3,
    coordinate: Coordinate,
): TicTacToe3x3MarkResult.Success {
    val result = game.markCell(coordinate = coordinate)
    assertTrue("Expected successful move", result is TicTacToe3x3MarkResult.Success)
    return result as TicTacToe3x3MarkResult.Success
}

/**
 * Helper function to print the grid based on the moves made.
 *
 * @param moves List of moves made in the game
 * @param currentMoveIndex Index of the current move (X-based)
 */
internal fun printGameBoardFromSetOfMoves(
    moves: Set<Coordinate>,
    currentMoveIndex: Int = moves.size - 1,
) {
    println("Game state after move $currentMoveIndex:")
    for (row in 0..2) {
        for (col in 0..2) {
            val moveIndex = moves.indexOfFirst { it.row == row && it.column == col }
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

internal fun printGameBoardFromSetOfMovesForAllStates(moves: Set<Coordinate>) {
    for (i in 0..8) {
        println("Move ${i + 1}:")
        printGameBoardFromSetOfMoves(moves, currentMoveIndex = i)
        println()
    }
}

/**
 * Helper function to print the current state of the game
 */
internal fun printTicTacToe3x3GameBoardState(game: TicTacToe3x3) {
    println("Current game state:")
    for (row in 0..2) {
        for (col in 0..2) {
            val cell = when (game.playerAt(coordinate = Coordinate.of(row = row, column = col))) {
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
