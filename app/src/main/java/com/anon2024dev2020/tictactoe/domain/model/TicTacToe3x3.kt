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
) {
    val state: TicTacToe3x3State =
        when {
            grid.winner != null -> TicTacToe3x3State.Victory(grid.winner!!)
            grid.isInProgress -> TicTacToe3x3State.InProgress
            grid.isDraw -> TicTacToe3x3State.Draw
            else -> throw IllegalStateException("Invalid game state")
        }

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

    fun getPlayerAt(coordinate: Coordinate): Player? = grid.getCell(coordinate = coordinate).player
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
                Grid3x3MarkResult.Grid3x3Error.OCCUPIED_CELL ->
                    TicTacToe3x3MarkResult.TicTacToe3x3Error.OCCUPIED_CELL

                Grid3x3MarkResult.Grid3x3Error.OUT_OF_BOUNDS ->
                    TicTacToe3x3MarkResult.TicTacToe3x3Error.OUT_OF_BOUNDS

                Grid3x3MarkResult.Grid3x3Error.GAME_OVER ->
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
        ),
    )
}
