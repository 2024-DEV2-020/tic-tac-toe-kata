package com.anon2024dev2020.tictactoe.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anon2024dev2020.tictactoe.R
import com.anon2024dev2020.tictactoe.ui.theme.TicTacToeTheme

@Composable
fun HomeScreen(screenName: String) {
    Column(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier,
            text = screenName,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

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
                    HomeScreen(screenName = "Home")
                }
            }
        }
    }
}
