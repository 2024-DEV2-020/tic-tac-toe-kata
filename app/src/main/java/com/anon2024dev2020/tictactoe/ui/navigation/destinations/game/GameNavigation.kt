package com.anon2024dev2020.tictactoe.ui.navigation.destinations.game

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.anon2024dev2020.tictactoe.R
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
    setGameScreenFABOnClick: (() -> Unit) -> Unit,
) {
    composable<Game> { navBackStackEntry ->
        // TODO: use
        val gameMode: GameMode = navBackStackEntry.toRoute<Game>().mode

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
            is GameViewModel.UiState.Success -> {
                GameScreen(
                    game = state.game,
                    xTotalGamesWon = state.xTotalGamesWon,
                    oTotalGamesWon = state.oTotalGamesWon,
                    totalDraws = state.totalDraws,
                )
            }

            else -> Text(
                modifier = Modifier.fillMaxSize(),
                text = stringResource(id = R.string.how_did_you_end_up_here_error_message),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
            )
        }
    }
}
