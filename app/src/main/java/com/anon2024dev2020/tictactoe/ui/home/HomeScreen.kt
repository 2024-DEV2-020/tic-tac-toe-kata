package com.anon2024dev2020.tictactoe.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anon2024dev2020.tictactoe.R
import com.anon2024dev2020.tictactoe.presentation.game.GameMode
import com.anon2024dev2020.tictactoe.ui.common.TicTacToeHeadingText
import com.anon2024dev2020.tictactoe.ui.theme.TicTacToeTheme

@Composable
fun HomeScreen(onGameModeClick: (GameMode) -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(top = 68.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TicTacToeHeadingText(modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.size(36.dp))

        val haptic = LocalHapticFeedback.current

        Button(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                onGameModeClick(GameMode.TWO_PLAYERS)
            },
        ) {
            Text(
                modifier = Modifier,
                text = "Player vs Player",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        Button(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                onGameModeClick(GameMode.VS_EASY_BOT)
            },
        ) {
            Text(
                modifier = Modifier,
                text = "vs Easy Bot",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        Button(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                onGameModeClick(GameMode.VS_IMPOSSIBLE_BOT)
            },
        ) {
            Text(
                modifier = Modifier,
                text = "vs Impossible Bot",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

// TODO: remove
@Composable
fun HomeScreenFABButton(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        contentColor = MaterialTheme.colorScheme.background,
        containerColor = MaterialTheme.colorScheme.onBackground,
        onClick = onClick,
    ) {
        Text(text = stringResource(id = R.string.fab_text_navigate_to_game))
    }
}

// @PreviewScreenSizes
@Preview
@Composable
private fun PreviewHomeScreen() {
    TicTacToeTheme {
        Surface {
            Scaffold(
                modifier =
                Modifier
                    .fillMaxSize(),
                floatingActionButton = {
                    HomeScreenFABButton {
                    }
                },
            ) { innerPadding ->
                Column(
                    modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                ) {
                    HomeScreen(onGameModeClick = {})
                }
            }
        }
    }
}
