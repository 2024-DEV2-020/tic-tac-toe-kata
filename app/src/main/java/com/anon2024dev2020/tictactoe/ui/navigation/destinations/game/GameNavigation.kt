package com.anon2024dev2020.tictactoe.ui.navigation.destinations.game

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.anon2024dev2020.tictactoe.presentation.game.GameViewModel
import com.anon2024dev2020.tictactoe.ui.game.GameScreen
import kotlinx.serialization.Serializable

@Serializable
object Game

internal fun NavController.navigateToGame() {
    navigate(route = Game) {
        launchSingleTop = true
    }
}

internal fun NavGraphBuilder.gameDestination(
    onNavigateToHome: () -> Unit,
    setGameScreenFABOnClick: (() -> Unit) -> Unit,
) {
    composable<Game> { _ ->
        val viewModel: GameViewModel = hiltViewModel()

        val haptic = LocalHapticFeedback.current

        LaunchedEffect(Unit) {
            setGameScreenFABOnClick {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                onNavigateToHome()
            }
        }

        val uiState = viewModel.uiState.collectAsState()

        when (val state = uiState.value) {
            is GameViewModel.UiState.Error -> GameScreen(
                screenName = "Error",
            )
            GameViewModel.UiState.Initial -> GameScreen(
                screenName = "Initial",
            )
            GameViewModel.UiState.Loading -> GameScreen(
                screenName = "Loading",
            )
            is GameViewModel.UiState.Success -> {
                GameScreen(
                    screenName = state.screenName,
                )
            }
        }
    }
}
