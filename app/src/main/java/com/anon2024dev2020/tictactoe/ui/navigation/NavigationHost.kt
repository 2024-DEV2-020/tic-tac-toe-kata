package com.anon2024dev2020.tictactoe.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import com.anon2024dev2020.tictactoe.ui.navigation.destinations.game.gameDestination
import com.anon2024dev2020.tictactoe.ui.navigation.destinations.game.navigateToGame
import com.anon2024dev2020.tictactoe.ui.navigation.destinations.home.Home
import com.anon2024dev2020.tictactoe.ui.navigation.destinations.home.homeDestination
import com.anon2024dev2020.tictactoe.ui.navigation.destinations.home.navigateToHome
import kotlinx.serialization.Serializable

// https://developer.android.com/develop/ui/compose/navigation
@Composable
fun NavigationHost(
    modifier: Modifier,
    navController: NavHostController,
    setHomeScreenFABButtonOnClick: (() -> Unit) -> Unit,
    setGameScreenFABButtonOnClick: (() -> Unit) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NestedGraphs.GameGraph,
    ) {
        navigation<NestedGraphs.GameGraph>(startDestination = Home) {
            homeDestination(
                onNavigateToGame = {
                    navController.navigateToGame()
                },
                setHomeScreenFABOnClick = setHomeScreenFABButtonOnClick,
            )

            gameDestination(
                onNavigateToHome = {
                    navController.navigateToHome()
                },
                setGameScreenFABOnClick = setGameScreenFABButtonOnClick,
            )
        }
    }
}

internal sealed class NestedGraphs {
    @Serializable
    object GameGraph
}

internal fun NavController.navigateToGameGraph() {
    navigate(route = NestedGraphs.GameGraph)
}
