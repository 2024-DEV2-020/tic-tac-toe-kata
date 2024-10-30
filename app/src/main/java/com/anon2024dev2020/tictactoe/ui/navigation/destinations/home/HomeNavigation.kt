package com.anon2024dev2020.tictactoe.ui.navigation.destinations.home

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.anon2024dev2020.tictactoe.presentation.home.HomeViewModel
import com.anon2024dev2020.tictactoe.ui.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
object Home

fun NavController.navigateToHome() {
    navigate(route = Home)
}

fun NavGraphBuilder.homeDestination(
    onNavigateToGame: () -> Unit,
    setHomeScreenFABOnClick: (() -> Unit) -> Unit,
) {
    composable<Home> {
        val viewModel: HomeViewModel = hiltViewModel()

        val haptic = LocalHapticFeedback.current

        LaunchedEffect(Unit) {
            setHomeScreenFABOnClick {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                onNavigateToGame()
            }
        }

        val uiState = viewModel.uiState.collectAsState()

        when (val state = uiState.value) {
            is HomeViewModel.UiState.Error -> HomeScreen(
                screenName = "Error",
            )

            HomeViewModel.UiState.Initial -> HomeScreen(
                screenName = "Initial",
            )

            HomeViewModel.UiState.Loading -> HomeScreen(
                screenName = "Loading",
            )

            is HomeViewModel.UiState.Success -> {
                HomeScreen(
                    screenName = state.screenName,
                )
            }
        }
    }
}
