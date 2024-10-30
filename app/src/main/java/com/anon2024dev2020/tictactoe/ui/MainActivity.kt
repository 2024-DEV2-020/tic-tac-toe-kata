package com.anon2024dev2020.tictactoe.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.anon2024dev2020.tictactoe.ui.game.GameScreenFABButton
import com.anon2024dev2020.tictactoe.ui.home.HomeScreen
import com.anon2024dev2020.tictactoe.ui.home.HomeScreenFABButton
import com.anon2024dev2020.tictactoe.ui.navigation.NavigationHost
import com.anon2024dev2020.tictactoe.ui.navigation.destinations.game.Game
import com.anon2024dev2020.tictactoe.ui.navigation.destinations.home.Home
import com.anon2024dev2020.tictactoe.ui.theme.TicTacToeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            // Strategy to set a FAB onClick, in a Single Activity App Architecture
            val (homeScreenFABButtonOnClick, setHomeScreenFABButtonOnClick) =
                remember {
                    mutableStateOf<(() -> Unit)?>(
                        null,
                    )
                }

            val (gameScreenFABButtonOnClick, setGameScreenFABButtonOnClick) =
                remember {
                    mutableStateOf<(() -> Unit)?>(
                        null,
                    )
                }

            val navController = rememberNavController()

            RootComposableAsScaffold(
                currentFloatingActionButton = {
                    CurrentFloatingActionButton(
                        currentDestination = navController
                            .currentBackStackEntryAsState()
                            .value
                            ?.destination,
                        homeScreenFABButtonOnClick = homeScreenFABButtonOnClick,
                        gameScreenFABButtonOnClick = gameScreenFABButtonOnClick,
                    )
                },
            ) { scaffoldInnerPadding ->
                NavigationHost(
                    modifier = Modifier.padding(scaffoldInnerPadding),
                    navController = navController,
                    setHomeScreenFABButtonOnClick = setHomeScreenFABButtonOnClick,
                    setGameScreenFABButtonOnClick = setGameScreenFABButtonOnClick,
                )
            }
        }
    }
}

@Composable
private fun RootComposableAsScaffold(
    modifier: Modifier = Modifier,
    currentFloatingActionButton: @Composable () -> Unit,
    rootContent: @Composable (scaffoldInnerPadding: PaddingValues) -> Unit,
) {
    TicTacToeTheme {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            floatingActionButton = {
                currentFloatingActionButton()
            },
        ) { innerPadding ->
            rootContent(innerPadding)
        }
    }
}

@Composable
fun CurrentFloatingActionButton(
    currentDestination: NavDestination?,
    homeScreenFABButtonOnClick: (() -> Unit)?,
    gameScreenFABButtonOnClick: (() -> Unit)?,
) {
    if (currentDestination?.hasRoute(Home::class) == true) {
        HomeScreenFABButton(onClick = homeScreenFABButtonOnClick ?: {})
    } else if (currentDestination?.hasRoute(Game::class) == true) {
        GameScreenFABButton(onClick = gameScreenFABButtonOnClick ?: {})
    }
}

@Preview
@Composable
fun PreviewRootComposable() {
    TicTacToeTheme {
        Surface {
            Column(
                modifier =
                Modifier
                    .fillMaxSize(),
            ) {
                RootComposableAsScaffold(currentFloatingActionButton = {
                    HomeScreenFABButton {
                    }
                }) {
                    HomeScreen(screenName = "Home")
                }
            }
        }
    }
}
