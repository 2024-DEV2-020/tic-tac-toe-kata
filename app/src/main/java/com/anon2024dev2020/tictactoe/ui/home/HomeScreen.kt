package com.anon2024dev2020.tictactoe.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anon2024dev2020.tictactoe.R
import com.anon2024dev2020.tictactoe.presentation.game.GameMode
import com.anon2024dev2020.tictactoe.ui.common.ClickableGlowingCard
import com.anon2024dev2020.tictactoe.ui.common.TicTacToeHeadingText
import com.anon2024dev2020.tictactoe.ui.theme.TicTacToeTheme

@Composable
fun HomeScreen(onGameModeClick: (GameMode) -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(top = 68.dp, start = 16.dp, end = 16.dp),
    ) {
        Column(
            Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TicTacToeHeadingText(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.size(48.dp))

            val haptic = LocalHapticFeedback.current
            GameModeButton(
                gameMode = GameMode.TWO_PLAYERS,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                glowingColor = MaterialTheme.colorScheme.primary,
                onGameModeClick = onGameModeClick,
                haptic = haptic,
            )

            Spacer(modifier = Modifier.size(32.dp))

            GameModeButton(
                gameMode = GameMode.VS_EASY_BOT,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                glowingColor = MaterialTheme.colorScheme.secondary,
                onGameModeClick = onGameModeClick,
                haptic = haptic,
            )

            Spacer(modifier = Modifier.size(32.dp))

            GameModeButton(
                gameMode = GameMode.VS_IMPOSSIBLE_BOT,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                glowingColor = MaterialTheme.colorScheme.tertiary,
                onGameModeClick = onGameModeClick,
                haptic = haptic,
            )
        }
    }
}

@Composable
private fun GameModeButton(
    gameMode: GameMode,
    containerColor: Color,
    contentColor: Color,
    glowingColor: Color,
    onGameModeClick: (GameMode) -> Unit,
    haptic: HapticFeedback,
) {
    ClickableGlowingCard(
        modifier = Modifier.width(260.dp),
        containerColor = containerColor,
        cornersRadius = 24.dp,
        glowingColor = glowingColor,
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            onGameModeClick(gameMode)
        },
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            PlayerOneIcon(Modifier.width(playerIconWidth))
            Text(
                text = stringResource(id = R.string.vs_label),
                style = MaterialTheme.typography.bodyLarge,
                color = contentColor,
            )
            OpponentIcon(modifier = Modifier.width(playerIconWidth), gameMode = gameMode)
        }
    }
}

private val playerIconWidth = 68.dp

@Composable
fun PlayerOneIcon(modifier: Modifier) {
    Box(modifier, contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.size(56.dp),
            painter = painterResource(id = R.drawable.player_1_icon),
            contentDescription = null,
        )
    }
}

@Composable
fun OpponentIcon(modifier: Modifier, gameMode: GameMode, tinyBadge: Boolean = false) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.size(56.dp),
            painter = painterResource(
                id = when (gameMode) {
                    GameMode.TWO_PLAYERS -> R.drawable.player_2_icon
                    GameMode.VS_EASY_BOT -> R.drawable.easy_bot_icon
                    GameMode.VS_IMPOSSIBLE_BOT -> R.drawable.impossible_bot_icon
                },
            ),
            contentDescription = null,
        )
        if (gameMode != GameMode.TWO_PLAYERS) {
            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(
                        y = if (tinyBadge && gameMode == GameMode.VS_EASY_BOT) {
                            8.dp
                        } else if (tinyBadge) 10.dp else 0.dp,
                    ),
                text = stringResource(
                    id = when (gameMode) {
                        GameMode.VS_EASY_BOT -> R.string.easy_bot_image_badge
                        GameMode.VS_IMPOSSIBLE_BOT -> {
                            if (tinyBadge) {
                                R.string.impossible_bot_image_badge
                            } else {
                                R.string.impossible_bot_image_badge_with_new_line
                            }
                        }

                        else -> throw IllegalArgumentException("Invalid game mode")
                    },
                ),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    fontSize = if (tinyBadge && gameMode == GameMode.VS_IMPOSSIBLE_BOT) {
                        6.sp
                    } else if (tinyBadge) 8.sp else 10.sp,
                    lineHeight = if (tinyBadge) 6.sp else 14.sp,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.5f),
                        offset = Offset(2f, 2f),
                        blurRadius = 3f,
                    ),
                    textAlign = TextAlign.Center,
                ),
                color = when (gameMode) {
                    GameMode.VS_EASY_BOT -> MaterialTheme.colorScheme.onSecondaryContainer
                    GameMode.VS_IMPOSSIBLE_BOT -> MaterialTheme.colorScheme.onTertiaryContainer
                    else -> throw IllegalArgumentException("Invalid game mode")
                },
            )
        }
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
