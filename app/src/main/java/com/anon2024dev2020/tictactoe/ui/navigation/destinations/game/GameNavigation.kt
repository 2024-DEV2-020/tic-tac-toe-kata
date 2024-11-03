package com.anon2024dev2020.tictactoe.ui.navigation.destinations.game

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.anon2024dev2020.tictactoe.presentation.game.GameMode
import com.anon2024dev2020.tictactoe.presentation.game.GameViewModel
import com.anon2024dev2020.tictactoe.ui.game.GameScreen
import kotlinx.serialization.Serializable

@Serializable
internal data class Game(val mode: GameMode)

internal fun NavController.navigateToGame(mode: GameMode) {
    navigate(route = Game(mode = mode)) {
        launchSingleTop = true
    }
}

internal fun NavGraphBuilder.gameDestination(
    onNavigateToHome: () -> Unit,
    setGameScreenUndoFABButtonOnClick: (() -> Unit) -> Unit,
    setGameScreenRestartFABButtonOnClick: (() -> Unit) -> Unit,
    setGameScreenGoHomeFABButtonOnClick: (() -> Unit) -> Unit,
) {
    composable<Game> { navBackStackEntry ->
        // TODO: use to create bot driver
        val gameMode: GameMode = navBackStackEntry.toRoute<Game>().mode

        val viewModel: GameViewModel = hiltViewModel()

        val haptic = LocalHapticFeedback.current

        val uiState = viewModel.uiState.collectAsState()

        when (val state = uiState.value) {
            is GameViewModel.UiState.Success -> {
                LaunchedEffect(Unit) {
                    setGameScreenUndoFABButtonOnClick {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        viewModel.onEvent(GameViewModel.UiEvent.UndoClick)
                    }
                    setGameScreenRestartFABButtonOnClick {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        viewModel.onEvent(GameViewModel.UiEvent.RestartClick)
                    }
                    setGameScreenGoHomeFABButtonOnClick {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        onNavigateToHome()
                    }
                }

                GameScreen(
                    game = state.game,
                    xTotalGamesWon = state.xTotalGamesWon,
                    oTotalGamesWon = state.oTotalGamesWon,
                    totalDraws = state.totalDraws,
                    gameMode = gameMode,
                    onCellClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        viewModel.onEvent(GameViewModel.UiEvent.CellClick(it))
                    },
                    onRestartClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        viewModel.onEvent(GameViewModel.UiEvent.RestartClick)
                    },
                )
            }
        }
    }
}
