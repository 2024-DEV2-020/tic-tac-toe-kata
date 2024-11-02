package com.anon2024dev2020.tictactoe.ui.navigation.destinations.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.anon2024dev2020.tictactoe.presentation.game.GameMode
import com.anon2024dev2020.tictactoe.ui.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
object Home

fun NavController.navigateToHome() {
    navigate(route = Home) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.homeDestination(onNavigateToGame: (GameMode) -> Unit) {
    composable<Home> {
        HomeScreen(onGameModeClick = onNavigateToGame)
    }
}
