package com.anon2024dev2020.tictactoe.domain.model

data class TicTacToe3x3(
    val currentPlayer: Player = Player.X,
    private val grid: Grid3x3 = Grid3x3(),
) {

    val state: TicTacToe3x3State = TicTacToe3x3State.InProgress // TODO: complete

    /**
     * Marks a cell in the Tic-Tac-Toe grid with the current player's symbol.
     *
     * @param row The row index of the cell to mark (0-2).
     * @param column The column index of the cell to mark (0-2).
     * @return A [TicTacToe3x3Result] indicating the success or failure of the operation.
     */
    fun markCell(row: Int, column: Int): TicTacToe3x3Result {
        val result = grid.markCell(player = currentPlayer, row = row, column = column)

        return when (result) {
            is Grid3x3.Grid3x3Result.Failure -> {
                TicTacToe3x3Result.Failure(
                    reason = when (result.reason) {
                        Grid3x3.Grid3x3Result.Grid3x3Error.OCCUPIED_CELL ->
                            TicTacToe3x3Result.TicTacToe3x3Error.OCCUPIED_CELL

                        Grid3x3.Grid3x3Result.Grid3x3Error.OUT_OF_BOUNDS ->
                            TicTacToe3x3Result.TicTacToe3x3Error.OUT_OF_BOUNDS
                    },
                )
            }

            is Grid3x3.Grid3x3Result.Success -> TicTacToe3x3Result.Success(
                actionPerformer = currentPlayer,
                updatedTicTacToe = this.copy(
                    currentPlayer = currentPlayer.opponent,
                    grid = result.updatedGrid,
                ),
            )
        }
    }

    fun getPlayerAtCell(row: Int, column: Int): Player? {
        return grid.getCell(row, column).player
    }

    sealed class TicTacToe3x3Result {
        data class Success(val actionPerformer: Player, val updatedTicTacToe: TicTacToe3x3) :
            TicTacToe3x3Result()

        data class Failure(val reason: TicTacToe3x3Error) : TicTacToe3x3Result()

        enum class TicTacToe3x3Error {
            OCCUPIED_CELL,
            OUT_OF_BOUNDS,
        }
    }

    sealed class TicTacToe3x3State {
        object InProgress : TicTacToe3x3State()
        data class Victory(val winner: Player) : TicTacToe3x3State()
        object Draw : TicTacToe3x3State()
    }
}
