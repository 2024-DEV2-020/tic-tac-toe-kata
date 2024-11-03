package com.anon2024dev2020.tictactoe.presentation.game

import com.anon2024dev2020.tictactoe.domain.model.Coordinate
import com.anon2024dev2020.tictactoe.domain.model.Player
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3
import com.anon2024dev2020.tictactoe.presentation.game.GameViewModel.UiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

/**
 * This Test Class follows the naming convention:
 * `thingUnderTest_TriggerOfTest_ResultOfTest`
 *
 * Where:
 * - `thingUnderTest` = `gameViewModel`
 * - `TriggerOfTest` = Various game actions (e.g., CellClick, RestartClick)
 * - `ResultOfTest` = Expected outcome (e.g., StateUpdated, ScoreIncremented)
 */
class GameViewModelTest {

    private lateinit var viewModel: GameViewModel
    private lateinit var initialGame: TicTacToe3x3

    @Before
    fun setup() {
        initialGame = TicTacToe3x3()
        viewModel = GameViewModel(initialGame)
    }

    @Test
    fun gameViewModel_InitialState_ShouldBeSuccess() {
        val initialState = viewModel.uiState.value

        assertEquals(
            UiState.Success(
                game = TicTacToe3x3(),
                xTotalGamesWon = 0,
                oTotalGamesWon = 0,
                totalDraws = 0,
            ),
            initialState,
        )
    }

    @Test
    fun gameViewModel_CellClick_ShouldUpdateGameStateCoordinate() {
        val coordinate00 = Coordinate.of(0, 0)
        val initialState = viewModel.uiState.value as UiState.Success
        assertNull(initialState.game.playerAt(coordinate00))

        viewModel.onEvent(GameViewModel.UiEvent.CellClick(coordinate00))

        val updatedState = viewModel.uiState.value as UiState.Success
        assertEquals(Player.X, updatedState.game.playerAt(coordinate00))
    }

    @Test
    fun gameViewModel_CellClick_ShouldUpdateGameStatePlayer() {
        val coordinate00 = Coordinate.of(0, 0)
        val initialState = viewModel.uiState.value as UiState.Success
        assertNull(initialState.game.playerAt(Coordinate.of(0, 0)))

        viewModel.onEvent(GameViewModel.UiEvent.CellClick(coordinate00))

        val updatedState = viewModel.uiState.value as UiState.Success
        assertEquals(Player.O, updatedState.game.currentPlayer)
    }

    @Test
    fun gameViewModel_RestartClickWhileInProgress_ShouldResetGameState() {
        viewModel.onEvent(GameViewModel.UiEvent.CellClick(Coordinate.of(0, 0)))

        viewModel.onEvent(GameViewModel.UiEvent.RestartClick)

        val updatedState = viewModel.uiState.value as UiState.Success
        assertEquals(TicTacToe3x3(), updatedState.game)
    }

    @Test
    fun gameViewModel_UndoClick_ShouldRevertLastMove() {
        viewModel.onEvent(GameViewModel.UiEvent.CellClick(Coordinate.of(0, 0)))

        viewModel.onEvent(GameViewModel.UiEvent.UndoClick)

        val updatedState = viewModel.uiState.value as UiState.Success
        assertNull(updatedState.game.playerAt(Coordinate.of(0, 0)))
    }

    @Test
    fun gameViewModel_UndoClickOnEmptyGame_ShouldNotChangeGameState() {
        val initialState = viewModel.uiState.value as UiState.Success

        viewModel.onEvent(GameViewModel.UiEvent.UndoClick)
        val stateAfterUndo = viewModel.uiState.value as UiState.Success

        assertEquals(initialState, stateAfterUndo)
    }

    @Test
    fun gameViewModel_XWins_ShouldIncrementXTotalGamesWon() {
        // X
        viewModel.onEvent(GameViewModel.UiEvent.CellClick(Coordinate.of(0, 0)))
        // O
        viewModel.onEvent(GameViewModel.UiEvent.CellClick(Coordinate.of(1, 0)))
        // X
        viewModel.onEvent(GameViewModel.UiEvent.CellClick(Coordinate.of(0, 1)))
        // O
        viewModel.onEvent(GameViewModel.UiEvent.CellClick(Coordinate.of(1, 1)))
        // X wins horizontal top
        viewModel.onEvent(GameViewModel.UiEvent.CellClick(Coordinate.of(0, 2)))

        val updatedState = viewModel.uiState.value as UiState.Success
        assertEquals(1, updatedState.xTotalGamesWon)
    }

    @Test
    fun gameViewModel_OWins_ShouldIncrementOTotalGamesWon() {
        // X
        viewModel.onEvent(GameViewModel.UiEvent.CellClick(Coordinate.of(0, 0)))
        // O
        viewModel.onEvent(GameViewModel.UiEvent.CellClick(Coordinate.of(1, 0)))
        // X
        viewModel.onEvent(GameViewModel.UiEvent.CellClick(Coordinate.of(2, 0)))
        // O
        viewModel.onEvent(GameViewModel.UiEvent.CellClick(Coordinate.of(1, 1)))
        // X
        viewModel.onEvent(GameViewModel.UiEvent.CellClick(Coordinate.of(2, 2)))
        // O wins diagonal
        viewModel.onEvent(GameViewModel.UiEvent.CellClick(Coordinate.of(1, 2)))

        val updatedState = viewModel.uiState.value as UiState.Success
        assertEquals(1, updatedState.oTotalGamesWon)
    }

    @Test
    fun gameViewModel_GameDraw_ShouldIncrementTotalDraws() {
//        | O | X | O |
//        |---|---|---|
//        | O | X | X |
//        |---|---|---|
//        | X | O | X |
        setOf(
            Coordinate.of(0, 1),
            Coordinate.of(0, 0),
            Coordinate.of(1, 1),
            Coordinate.of(0, 2),
            Coordinate.of(1, 2),
            Coordinate.of(1, 0),
            Coordinate.of(2, 0),
            Coordinate.of(2, 1),
            Coordinate.of(2, 2),
        ).forEach { viewModel.onEvent(GameViewModel.UiEvent.CellClick(it)) }

        val updatedState = viewModel.uiState.value as UiState.Success
        assertEquals(1, updatedState.totalDraws)
    }

    @Test
    fun gameViewModel_CellClickOnOccupiedCell_ShouldNotChangeGameState() {
        val coordinate = Coordinate.of(0, 0)
        viewModel.onEvent(GameViewModel.UiEvent.CellClick(coordinate))
        val stateAfterFirstClick = viewModel.uiState.value as UiState.Success

        viewModel.onEvent(GameViewModel.UiEvent.CellClick(coordinate))
        val stateAfterSecondClick = viewModel.uiState.value as UiState.Success

        assertEquals(stateAfterFirstClick, stateAfterSecondClick)
    }

    @Test
    fun gameViewModel_RestartClickAfterXWin_ShouldNotResetTotalGamesWon() {
        // Make X win
        // Final game board:
        // X X X
        // O O -
        // - - -
        val moves = setOf(
            // X plays
            Coordinate.of(0, 0),
            // O plays
            Coordinate.of(1, 0),
            // X plays
            Coordinate.of(0, 1),
            // O plays
            Coordinate.of(1, 1),
            // X wins with horizontal top
            Coordinate.of(0, 2),
        )
        moves.forEach { viewModel.onEvent(GameViewModel.UiEvent.CellClick(it)) }

        viewModel.onEvent(GameViewModel.UiEvent.RestartClick)

        val updatedState = viewModel.uiState.value as UiState.Success
        assertEquals(1, updatedState.xTotalGamesWon)
    }

    @Test
    fun gameViewModel_RestartClickAfterOWin_ShouldNotResetTotalGamesWon() {
        // Make O win
        // Final game board:
        // X X O
        // - - O
        // X - O
        val moves = setOf(
            // X plays
            Coordinate.of(0, 0),
            // O plays
            Coordinate.of(2, 2),
            // X plays
            Coordinate.of(0, 1),
            // O plays
            Coordinate.of(1, 2),
            // X plays
            Coordinate.of(2, 0),
            // O wins with vertical right
            Coordinate.of(0, 2),
        )
        moves.forEach { viewModel.onEvent(GameViewModel.UiEvent.CellClick(it)) }

        viewModel.onEvent(GameViewModel.UiEvent.RestartClick)

        val updatedState = viewModel.uiState.value as UiState.Success
        assertEquals(1, updatedState.oTotalGamesWon)
    }

    @Test
    fun gameViewModel_RestartClickAfterDraw_ShouldNotResetTotalGamesDrawn() {
//        | O | X | O |
//        |---|---|---|
//        | O | X | X |
//        |---|---|---|
//        | X | O | X |
        val movesForDraw = setOf(
            Coordinate.of(0, 1),
            Coordinate.of(0, 0),
            Coordinate.of(1, 1),
            Coordinate.of(0, 2),
            Coordinate.of(1, 2),
            Coordinate.of(1, 0),
            Coordinate.of(2, 0),
            Coordinate.of(2, 1),
            Coordinate.of(2, 2),
        )
        movesForDraw.forEach { viewModel.onEvent(GameViewModel.UiEvent.CellClick(it)) }

        viewModel.onEvent(GameViewModel.UiEvent.RestartClick)

        val updatedState = viewModel.uiState.value as UiState.Success
        assertEquals(1, updatedState.totalDraws)
    }

    @Test
    fun gameViewModel_MultipleGamesWithDifferentOutcomes_ShouldUpdateAllCounters() {
        // X wins
        setOf(
            Coordinate.of(0, 1),
            Coordinate.of(1, 0),
            Coordinate.of(0, 0),
            Coordinate.of(2, 2),
            Coordinate.of(0, 2),
        ).forEach { viewModel.onEvent(GameViewModel.UiEvent.CellClick(it)) }

        viewModel.onEvent(GameViewModel.UiEvent.RestartClick)

        // O wins
        setOf(
            Coordinate.of(1, 0),
            Coordinate.of(0, 1),
            Coordinate.of(2, 2),
            Coordinate.of(0, 0),
            Coordinate.of(2, 1),
            Coordinate.of(0, 2),
        ).forEach { viewModel.onEvent(GameViewModel.UiEvent.CellClick(it)) }
        viewModel.onEvent(GameViewModel.UiEvent.RestartClick)

        // Draw
        setOf(
            Coordinate.of(0, 1),
            Coordinate.of(0, 0),
            Coordinate.of(1, 1),
            Coordinate.of(0, 2),
            Coordinate.of(1, 2),
            Coordinate.of(1, 0),
            Coordinate.of(2, 0),
            Coordinate.of(2, 1),
            Coordinate.of(2, 2),
        ).forEach { viewModel.onEvent(GameViewModel.UiEvent.CellClick(it)) }

        val finalState = viewModel.uiState.value as UiState.Success
        assertEquals(1, finalState.xTotalGamesWon)
        assertEquals(1, finalState.oTotalGamesWon)
        assertEquals(1, finalState.totalDraws)
    }
}
