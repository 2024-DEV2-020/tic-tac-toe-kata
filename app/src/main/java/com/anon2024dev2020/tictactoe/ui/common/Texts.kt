package com.anon2024dev2020.tictactoe.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.anon2024dev2020.tictactoe.R
import com.anon2024dev2020.tictactoe.ui.theme.TicTacToeTheme

@Composable
fun TicTacToeHeadingText(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.tic_tac_toe_heading),
        style = MaterialTheme.typography.displayLarge.copy(
            shadow = Shadow(
                color = MaterialTheme.colorScheme.onBackground,
                offset = Offset(4f, 4f),
                blurRadius = 4f,
            ),
        ),
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center,
    )
}

@Preview
@Composable
fun PreviewTicTacToeHeadingText() {
    TicTacToeTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                TicTacToeHeadingText()
            }
        }
    }
}
