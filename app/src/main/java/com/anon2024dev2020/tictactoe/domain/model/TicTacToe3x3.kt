package com.anon2024dev2020.tictactoe.domain.model

data class TicTacToe3x3(
    private val grid: Grid3x3 = Grid3x3(),
) {
    val currentPlayer: Player = Player.X

//    fun getPosition(row: Int, col: Int): Player?
//    fun placeMark(int): GameMarkPlacementResult
//    fun getState(): GameState

    /**
     * Marks a cell in the Tic-Tac-Toe grid with the current player's symbol.
     *
     * @param row The row index of the cell to mark (0-2).
     * @param column The column index of the cell to mark (0-2).
     * @return A [TicTacToe3x3Result] indicating the success or failure of the operation.
     */
    fun markCell(row: Int, column: Int): TicTacToe3x3Result {
        TODO()
    }

    fun getCell(row: Int, column: Int): Player? {
        TODO()
    }

    sealed class TicTacToe3x3Result {
        data class Success(val actionPerformer: Player, val updatedTicTacToe: TicTacToe3x3) :
            TicTacToe3x3Result()

        data class Failure(val reason: TicTacToe3x3Error) : TicTacToe3x3Result()

        enum class TicTacToe3x3Error {
            INVALID_MARK
        }
    }
}
