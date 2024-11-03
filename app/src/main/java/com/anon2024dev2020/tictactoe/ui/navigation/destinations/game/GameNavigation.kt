package com.anon2024dev2020.tictactoe.ui.navigation.destinations.game

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.anon2024dev2020.tictactoe.domain.model.Coordinate
import com.anon2024dev2020.tictactoe.domain.model.Player
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3
import com.anon2024dev2020.tictactoe.domain.model.ai.TicTacToe3x3AIEasy
import com.anon2024dev2020.tictactoe.domain.model.ai.TicTacToe3x3AIMarkResult
import com.anon2024dev2020.tictactoe.presentation.game.GameMode
import com.anon2024dev2020.tictactoe.presentation.game.GameViewModel
import com.anon2024dev2020.tictactoe.ui.game.GameScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

                when (gameMode) {
                    GameMode.TWO_PLAYERS -> Unit
                    GameMode.VS_EASY_BOT -> {
                        TicTacToe3x3AIEasyDriver(
                            game = state.game,
                            onCellClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                viewModel.onEvent(GameViewModel.UiEvent.CellClick(it))
                            },
                        )
                    }
                    GameMode.VS_IMPOSSIBLE_BOT -> TODO("Implement impossible bot")
                }

                GameScreen(
                    game = state.game,
                    xTotalGamesWon = state.xTotalGamesWon,
                    oTotalGamesWon = state.oTotalGamesWon,
                    totalDraws = state.totalDraws,
                    gameMode = gameMode,
                    onCellClick = {
                        if (state.game.currentPlayer == Player.X ||
                            gameMode == GameMode.TWO_PLAYERS
                        ) {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            viewModel.onEvent(GameViewModel.UiEvent.CellClick(it))
                        }
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

@Composable
private fun TicTacToe3x3AIEasyDriver(game: TicTacToe3x3, onCellClick: (Coordinate) -> Unit) {
    val bot = remember { TicTacToe3x3AIEasy() }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    if (game.currentPlayer == Player.O) {
        LaunchedEffect(game) {
            scope.launch {
                // Add a small delay to simulate "thinking"
                delay(300)
                when (val result = bot.markCell(game)) {
                    is TicTacToe3x3AIMarkResult.Success -> onCellClick(result.coordinate)
                    is TicTacToe3x3AIMarkResult.Failure -> {
                        Toast.makeText(context, "Bot is Broken :(", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
