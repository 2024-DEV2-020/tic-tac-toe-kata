package com.anon2024dev2020.tictactoe.model

import com.anon2024dev2020.tictactoe.domain.model.Coordinate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class CoordinateTest {

    @Test
    fun `create coordinate using of method`() {
        val coord = Coordinate.of(1, 2)
        assertEquals(1, coord.x)
        assertEquals(2, coord.y)
    }

    @Test
    fun `create coordinate using primary constructor`() {
        val coord = Coordinate(Pair(3, 4))
        assertEquals(3, coord.x)
        assertEquals(4, coord.y)
    }

    @Test
    fun `coordinates with same values should be equal`() {
        val coord1 = Coordinate.of(5, 6)
        val coord2 = Coordinate.of(5, 6)
        assertEquals(coord1, coord2)
    }

    @Test
    fun `coordinates with different values should not be equal`() {
        val coord1 = Coordinate.of(1, 2)
        val coord2 = Coordinate.of(2, 1)
        assertNotEquals(coord1, coord2)
    }
}
