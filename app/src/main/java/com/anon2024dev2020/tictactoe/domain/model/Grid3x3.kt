package com.anon2024dev2020.tictactoe.domain.model

/**
 * Represents a 3x3 grid for a Tic-Tac-Toe game.
 *
 * This class is stateless and immutable. Each operation that would modify the grid
 * returns a new instance of [Grid3x3] instead of modifying the existing one.
 *
 * @property grid3x3 A 3x3 list of lists representing the game grid.
 */
data class Grid3x3(
    private val grid3x3: List<List<Grid3x3Cell>> = List(ROWS) { List(COLUMNS) { Grid3x3Cell() } },
) {
    init {
        require(grid3x3.size == ROWS && grid3x3.all { it.size == COLUMNS }) {
            "Grid must be exactly 3x3"
        }
    }

    val winner: Player?
        get() {
//        HORIZONTAL_TOP
            val playerCell00 = grid3x3[0][0].player
            if (playerCell00 != null &&
                playerCell00 == grid3x3[0][1].player && playerCell00 == grid3x3[0][2].player
            ) {
                return playerCell00
            }

//        HORIZONTAL_MIDDLE,
            val playerCell10 = grid3x3[1][0].player
            if (playerCell10 != null &&
                playerCell10 == grid3x3[1][1].player && playerCell10 == grid3x3[1][2].player
            ) {
                return playerCell10
            }

//        HORIZONTAL_BOTTOM,
            val playerCell20 = grid3x3[2][0].player
            if (playerCell20 != null &&
                playerCell20 == grid3x3[2][1].player && playerCell20 == grid3x3[2][2].player
            ) {
                return playerCell20
            }

//        VERTICAL_LEFT,
            if (playerCell00 != null &&
                playerCell00 == grid3x3[1][0].player && playerCell00 == grid3x3[2][0].player
            ) {
                return playerCell00
            }

//        VERTICAL_MIDDLE,
            val playerCell01 = grid3x3[0][1].player
            if (playerCell01 != null &&
                playerCell01 == grid3x3[1][1].player && playerCell01 == grid3x3[2][1].player
            ) {
                return playerCell01
            }

//        VERTICAL_RIGHT,
            val playerCell02 = grid3x3[0][2].player
            if (playerCell02 != null &&
                playerCell02 == grid3x3[1][2].player && playerCell02 == grid3x3[2][2].player
            ) {
                return playerCell02
            }

//        DIAGONAL_TOP_LEFT_TO_BOTTOM_RIGHT,
            if (playerCell00 != null &&
                playerCell00 == grid3x3[1][1].player && playerCell00 == grid3x3[2][2].player
            ) {
                return playerCell00
            }

//        DIAGONAL_TOP_RIGHT_TO_BOTTOM_LEFT,
            if (playerCell02 != null &&
                playerCell02 == grid3x3[1][1].player && playerCell02 == grid3x3[2][0].player
            ) {
                return playerCell02
            }

            return null
        }

    val isDraw: Boolean
        get() = winner == null && grid3x3.all { row -> row.all { cell -> cell.player != null } }

    val isInProgress: Boolean
        get() = winner == null && !isDraw

    fun getCell(coordinate: Coordinate): Grid3x3Cell {
        require(coordinate.x in 0 until ROWS && coordinate.y in 0 until COLUMNS) {
            "Invalid cell coordinates: x=${coordinate.x}, y=${coordinate.y}"
        }
        return grid3x3[coordinate.x][coordinate.y]
    }

    /**
     * Marks a cell in the 3x3 grid with the specified player's symbol.
     *
     * @param player The [Player] making the move.
     * @param coordinate The [Coordinate] of the cell to mark.
     * @return A [Grid3x3MarkResult] indicating the success or failure of the operation.
     *         If successful, it contains a new [Grid3x3] instance with the updated state.
     *         If unsuccessful, it contains an error indicating the reason for failure.
     */
    fun markCell(player: Player, coordinate: Coordinate): Grid3x3MarkResult {
        if (!isInProgress) {
            return Grid3x3MarkResult.Failure(Grid3x3MarkResult.Grid3x3Error.GAME_OVER)
        }

        if (coordinate.x !in 0 until ROWS || coordinate.y !in 0 until COLUMNS) {
            return Grid3x3MarkResult.Failure(Grid3x3MarkResult.Grid3x3Error.OUT_OF_BOUNDS)
        }

        if (getCell(coordinate).player != null) {
            return Grid3x3MarkResult.Failure(Grid3x3MarkResult.Grid3x3Error.OCCUPIED_CELL)
        }

        return Grid3x3MarkResult.Success(
            updatedGrid = this.copy(
                grid3x3 = grid3x3.mapIndexed { rowIndex, existingRowList ->
                    if (rowIndex == coordinate.x) {
                        existingRowList.mapIndexed { colIndex, existingCell ->
                            if (colIndex == coordinate.y) Grid3x3Cell(player) else existingCell
                        }
                    } else {
                        existingRowList
                    }
                },
            ),
        )
    }

    companion object {
        private const val COLUMNS = 3
        private const val ROWS = 3
    }
}
