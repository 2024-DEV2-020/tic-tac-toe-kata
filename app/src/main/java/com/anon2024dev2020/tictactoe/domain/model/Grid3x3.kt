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
    private val grid3x3: List<List<Grid3x3Cell>> = List(ROWS) { row ->
        List(COLUMNS) { col ->
            Grid3x3Cell(value = null, coordinate = Coordinate.of(row, col))
        }
    },
) {
    init {
        require(grid3x3.size == ROWS && grid3x3.all { it.size == COLUMNS }) {
            "Grid must be exactly 3x3"
        }
    }

    val winner: Player?
        get() {
            for (pattern in winningPatterns) {
                val (coordinate1, coordinate2, coordinate3) = pattern.toList()
                val playerAtCoordinate1 = getCell(coordinate1).value
                if (playerAtCoordinate1 != null &&
                    playerAtCoordinate1 == getCell(coordinate2).value &&
                    playerAtCoordinate1 == getCell(coordinate3).value
                ) {
                    return playerAtCoordinate1
                }
            }
            return null
        }

    val isDraw: Boolean
        get() = !grid3x3.any { row -> row.any { cell -> cell.value == null } } && winner == null

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
            return Grid3x3MarkResult.Failure(Grid3x3MarkResult.Grid3x3MarkError.GAME_OVER)
        }

        if (coordinate.x !in 0 until ROWS || coordinate.y !in 0 until COLUMNS) {
            return Grid3x3MarkResult.Failure(Grid3x3MarkResult.Grid3x3MarkError.OUT_OF_BOUNDS)
        }

        if (getCell(coordinate).value != null) {
            return Grid3x3MarkResult.Failure(Grid3x3MarkResult.Grid3x3MarkError.OCCUPIED_CELL)
        }

        return Grid3x3MarkResult.Success(
            updatedGrid = this.copy(
                grid3x3 = grid3x3.map { row ->
                    row.map { cell ->
                        if (cell.coordinate == coordinate) cell.copy(value = player) else cell
                    }
                },
            ),
        )
    }

    companion object {
        private const val COLUMNS = 3
        private const val ROWS = 3

        private val winningPatterns = setOf(
            // Rows
            setOf(Coordinate.of(0, 0), Coordinate.of(0, 1), Coordinate.of(0, 2)),
            setOf(Coordinate.of(1, 0), Coordinate.of(1, 1), Coordinate.of(1, 2)),
            setOf(Coordinate.of(2, 0), Coordinate.of(2, 1), Coordinate.of(2, 2)),
            // Columns
            setOf(Coordinate.of(0, 0), Coordinate.of(1, 0), Coordinate.of(2, 0)),
            setOf(Coordinate.of(0, 1), Coordinate.of(1, 1), Coordinate.of(2, 1)),
            setOf(Coordinate.of(0, 2), Coordinate.of(1, 2), Coordinate.of(2, 2)),
            // Diagonals
            setOf(Coordinate.of(0, 0), Coordinate.of(1, 1), Coordinate.of(2, 2)),
            setOf(Coordinate.of(0, 2), Coordinate.of(1, 1), Coordinate.of(2, 0)),
        )
    }
}
