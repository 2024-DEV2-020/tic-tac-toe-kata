package com.anon2024dev2020.tictactoe.domain.model

data class Grid3x3(
    private val grid3x3: List<List<Cell>> = List(ROWS) { List(COLUMNS) { Cell() } },
) {

    fun getCell(row: Int, column: Int): Cell {
        require(row in 0 until ROWS && column in 0 until COLUMNS) {
            "Invalid cell coordinates: row=$row, column=$column"
        }
        return grid3x3[row][column]
    }

    fun markCell(player: Player, row: Int, column: Int): Grid3x3Result {
        if (row < 0 || row >= ROWS || column < 0 || column >= COLUMNS) {
            return Grid3x3Result.Failure(Grid3x3Result.Grid3x3Error.OUT_OF_BOUNDS)
        }

        if (getCell(row, column).player != null) {
            return Grid3x3Result.Failure(Grid3x3Result.Grid3x3Error.OCCUPIED_CELL)
        }

        return Grid3x3Result.Success(
            updatedGrid = this.copy(
                grid3x3 = grid3x3.mapIndexed { rowIndex, existingRowList ->
                    if (rowIndex == row) {
                        existingRowList.mapIndexed { colIndex, existingCell ->
                            if (colIndex == column) Cell(player) else existingCell
                        }
                    } else {
                        existingRowList
                    }
                },
            ),
        )
    }

    sealed class Grid3x3Result {
        data class Success(val updatedGrid: Grid3x3) : Grid3x3Result()
        data class Failure(val reason: Grid3x3Error) : Grid3x3Result()

        enum class Grid3x3Error {
            OCCUPIED_CELL,
            OUT_OF_BOUNDS,
        }
    }

    companion object {
        private const val COLUMNS = 3
        private const val ROWS = 3
    }
}
