package com.anon2024dev2020.tictactoe.domain.model

/**
 * Represents a 3x3 Tic-Tac-Toe game.
 *
 * This class is stateless and immutable. Each operation that would modify the game state
 * returns a new instance of [TicTacToe3x3] instead of modifying the existing one.
 *
 * @property currentPlayer The player whose turn it is to make a move.
 * @property grid The 3x3 grid representing the game board.
 */
data class TicTacToe3x3(
    val currentPlayer: Player = Player.X,
    private val grid: Grid3x3 = Grid3x3(),
    internal val history: List<Grid3x3> = emptyList(),
) {

    /**
     * The current state of the Tic-Tac-Toe game.
     *
     * @return [TicTacToe3x3State.Victory] if there's a winner,
     *         [TicTacToe3x3State.InProgress] if the game is ongoing,
     *         [TicTacToe3x3State.Draw] if it's a draw.
     * @throws IllegalStateException if an invalid state is encountered.
     */
    val state: TicTacToe3x3State =
        when {
            grid.winner != null -> TicTacToe3x3State.Victory(grid.winner!!)
            grid.isInProgress -> TicTacToe3x3State.InProgress
            grid.isDraw -> TicTacToe3x3State.Draw
            else -> throw IllegalStateException("Invalid game state")
        }

    /**
     * Returns a list of all cells in the Tic-Tac-Toe grid.
     *
     * The cells are ordered row by row, from top to bottom and left to right.
     * The order corresponds to the following grid positions:
     *
     * (0,0) (0,1) (0,2)
     * (1,0) (1,1) (1,2)
     * (2,0) (2,1) (2,2)
     *
     * Where (row, column) represents the coordinate of each cell.
     */
    val cells: List<Grid3x3Cell>
        get() = grid.cells

    /**
     * Returns a list of all empty cells (cells with null value) in the grid.
     */
    val emptyCells: List<Grid3x3Cell>
        get() = grid.emptyCells

    /**
     * Marks a cell in the Tic-Tac-Toe grid with the current player's symbol.
     *
     * @param coordinate The [Coordinate] of the cell to mark.
     * @return A [TicTacToe3x3MarkResult] indicating the success or failure of the operation.
     *         If successful, it contains a new [TicTacToe3x3] instance with the updated state.
     */
    fun markCell(coordinate: Coordinate): TicTacToe3x3MarkResult =
        grid.markCell(player = currentPlayer, coordinate = coordinate)
            .mapToTicTacToe3x3MarkResult(
                currentPlayer = currentPlayer,
                currentGameState = this,
            )

    /**
     * Undoes the last move made in the game.
     *
     * @return A [TicTacToe3x3UndoResult] indicating success (containing updated state)
     *  or failure of the undo operation.
     */
    fun undo(): TicTacToe3x3UndoResult {
        if (history.isEmpty()) {
            return TicTacToe3x3UndoResult.Failure(
                reason = TicTacToe3x3UndoResult.TicTacToe3x3UndoError.NO_MOVES_TO_UNDO,
            )
        }

        if (state !is TicTacToe3x3State.InProgress) {
            return TicTacToe3x3UndoResult.Failure(
                reason = TicTacToe3x3UndoResult.TicTacToe3x3UndoError.GAME_OVER,
            )
        }

        return if (history.size == 1) {
            TicTacToe3x3UndoResult.Success(
                updatedTicTacToe = this.copy(
                    currentPlayer = currentPlayer.opponent,
                    grid = Grid3x3(),
                    history = emptyList(),
                ),
            )
        } else {
            val previousGames = history.dropLast(1)
            TicTacToe3x3UndoResult.Success(
                updatedTicTacToe = this.copy(
                    currentPlayer = currentPlayer.opponent,
                    grid = previousGames.last(),
                    history = previousGames,
                ),
            )
        }
    }

    /**
     * Retrieves the move made at a specific turn in the game's history.
     *
     * @param turnIndex The index of the turn in the game's history (0-based, LIFO).
     * @return The [Grid3x3Cell] representing the move made at the specified turn.
     */
    fun getMoveMadeAt(turnIndex: Int): Grid3x3Cell {
        require(turnIndex >= 0 && turnIndex < history.size) {
            "Turn index out of bounds: $turnIndex. History size: ${history.size}"
        }
//        val gridAtTurn = history[turnIndex]
//        if (turnIndex == 0) {
//            // If one turn has been played, there will only be one marked cell
//            return gridAtTurn.grid3x3.flatten().first { it.value != null }
//        } else {
//            // If multiple turns have been played, extract the diff
//            val previousGrid = history[turnIndex - 1]
//
//            val changedCell = gridAtTurn.grid3x3
//                .flatten()
//                .zip(previousGrid.grid3x3.flatten())
//                .find { (currentCell, previousCell) -> currentCell.value != previousCell.value }
//                ?.first
//
//            return changedCell
//                ?: throw IllegalStateException("No cell change found between consecutive turns")
//        } d
        TODO()
    }

    /**
     * Retrieves the player occupying the cell at the specified coordinate.
     *
     * @param coordinate The [Coordinate] of the cell to check.
     * @return The [Player] occupying the cell, or null if the cell is empty.
     */
    fun playerAt(coordinate: Coordinate): Player? = grid.cellAt(coordinate = coordinate).value
}

/**
 * Maps a [Grid3x3MarkResult] to a [TicTacToe3x3MarkResult].
 *
 * @param currentPlayer The current player making the move.
 * @param currentGameState The current state of the TicTacToe3x3 game.
 *
 * @return A [TicTacToe3x3MarkResult] representing either:
 * - [TicTacToe3x3MarkResult.Failure] with the corresponding error reason.
 *   (Note: [TicTacToe3x3] is stateless, meaning nothing changes to the original object)
 * - [TicTacToe3x3MarkResult.Success] containing:
 *    - The updated game state with updated grid object
 *    - The next player (if still in progress)
 */
private fun Grid3x3MarkResult.mapToTicTacToe3x3MarkResult(
    currentPlayer: Player,
    currentGameState: TicTacToe3x3,
): TicTacToe3x3MarkResult = when (this) {
    is Grid3x3MarkResult.Failure -> {
        TicTacToe3x3MarkResult.Failure(
            reason = when (this.reason) {
                Grid3x3MarkResult.Grid3x3MarkError.OCCUPIED_CELL ->
                    TicTacToe3x3MarkResult.TicTacToe3x3Error.OCCUPIED_CELL

                Grid3x3MarkResult.Grid3x3MarkError.OUT_OF_BOUNDS ->
                    TicTacToe3x3MarkResult.TicTacToe3x3Error.OUT_OF_BOUNDS

                Grid3x3MarkResult.Grid3x3MarkError.GAME_OVER ->
                    TicTacToe3x3MarkResult.TicTacToe3x3Error.GAME_OVER
            },
        )
    }

    is Grid3x3MarkResult.Success -> TicTacToe3x3MarkResult.Success(
        actionPerformer = currentPlayer,
        updatedTicTacToe = currentGameState.copy(
            currentPlayer = if (this.updatedGrid.isInProgress) {
                currentPlayer.opponent
            } else {
                currentPlayer
            },
            grid = this.updatedGrid,
            history = currentGameState.history + this.updatedGrid,
        ),
    )
}
