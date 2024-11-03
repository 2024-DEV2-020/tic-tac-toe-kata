package com.anon2024dev2020.tictactoe.ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anon2024dev2020.tictactoe.R
import com.anon2024dev2020.tictactoe.domain.model.Coordinate
import com.anon2024dev2020.tictactoe.domain.model.Grid3x3Cell
import com.anon2024dev2020.tictactoe.domain.model.Player
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3MarkResult
import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3State
import com.anon2024dev2020.tictactoe.presentation.game.GameMode
import com.anon2024dev2020.tictactoe.ui.common.ClickableGlowingCard
import com.anon2024dev2020.tictactoe.ui.common.GlowingCard
import com.anon2024dev2020.tictactoe.ui.home.OpponentIcon
import com.anon2024dev2020.tictactoe.ui.home.PlayerOneIcon
import com.anon2024dev2020.tictactoe.ui.theme.TicTacToeColors
import com.anon2024dev2020.tictactoe.ui.theme.TicTacToeTheme

@Composable
fun GameScreen(
    game: TicTacToe3x3,
    xTotalGamesWon: Int,
    oTotalGamesWon: Int,
    totalDraws: Int,
    gameMode: GameMode,
    onCellClick: (Coordinate) -> Unit,
    onRestartClick: () -> Unit,
) {
    GameOverDialog(
        gameState = game.state,
        xTotalGamesWon = xTotalGamesWon,
        oTotalGamesWon = oTotalGamesWon,
        totalDraws = totalDraws,
        onRestartClick = onRestartClick,
    )

    Column(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(modifier = Modifier) {
            PlayerAndScoreInformation(
                player = Player.X,
                gameMode = gameMode,
                xTotalGamesWon,
            )

            Spacer(modifier = Modifier.size(16.dp))

            PlayerAndScoreInformation(
                player = Player.O,
                gameMode = gameMode,
                oTotalGamesWon,
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.number_of_draws_label, totalDraws.toString()),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.size(16.dp))

        TicTacToe3x3Grid(cells = game.cells, onClick = onCellClick)

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            modifier = Modifier,
            text = if (game.currentPlayer == Player.X) {
                stringResource(id = R.string.x_mark_in_grid)
            } else {
                stringResource(id = R.string.o_mark_in_grid)
            },
            style = MaterialTheme.typography.displayLarge.copy(
                fontSize = 38.sp,
                shadow = Shadow(
                    color = if (game.currentPlayer == Player.X) {
                        TicTacToeColors.Player1.mainDark
                    } else {
                        TicTacToeColors.Player2.mainDark
                    },
                    offset = Offset(6f, 6f),
                    blurRadius = 8f,
                ),
            ),
            color = if (game.currentPlayer == Player.X) {
                TicTacToeColors.Player1.main
            } else {
                TicTacToeColors.Player2.main
            },
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun VictoryDialog(
    winner: Player?,
    victoryCount: Int,
    onDismiss: () -> Unit,
    onRestart: () -> Unit,
) {
    val backgroundColor =
        if (winner == null) {
            MaterialTheme.colorScheme.surfaceBright
        } else if (winner == Player.X) {
            TicTacToeColors.Player1.main
        } else {
            TicTacToeColors.Player2.main
        }
    val textColor =
        if (winner == null) {
            MaterialTheme.colorScheme.onSurface
        } else if (winner == Player.X) {
            TicTacToeColors.Player1.accentDark
        } else {
            TicTacToeColors.Player2.mainDark
        }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = backgroundColor,
        title = {
            Text(
                text = if (winner == null) {
                    stringResource(R.string.draw_dialog_title)
                } else {
                    stringResource(
                        R.string.victory_dialog_title,
                        winner.name,
                    )
                },
                style = MaterialTheme.typography.titleMedium.copy(
                    color = textColor,
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = if (winner == null) {
                        stringResource(
                            R.string.draw_dialog_count_body_text,
                            victoryCount.toString(),
                        )
                    } else {
                        stringResource(R.string.victory_dialog_message, victoryCount.toString())
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor,
                    textAlign = TextAlign.Center,
                )
                winner?.let {
                    Spacer(modifier = Modifier.size(16.dp))
                    Image(
                        painter = painterResource(id = R.drawable.winner_icon),
                        contentDescription = "Victory Icon",
                        modifier = Modifier.size(64.dp),
                    )
                }
            }
        },
        confirmButton = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Image(
                    modifier = Modifier
                        .width(200.dp)
                        .height(42.dp)
                        .clickable { onRestart() },
                    painter = painterResource(id = R.drawable.new_game_button),
                    contentDescription = stringResource(
                        id = R.string.new_game_btn_desc,
                    ),
                )
            }
        },
    )
}

@Composable
fun GameOverDialog(
    gameState: TicTacToe3x3State,
    xTotalGamesWon: Int,
    oTotalGamesWon: Int,
    totalDraws: Int,
    onRestartClick: () -> Unit,
) {
    val gameOverDialogInfo = when (gameState) {
        is TicTacToe3x3State.Draw -> GameOverDialogInfo(null, totalDraws)
        is TicTacToe3x3State.Victory -> GameOverDialogInfo(
            gameState.winner,
            if (gameState.winner == Player.X) xTotalGamesWon else oTotalGamesWon,
        )

        is TicTacToe3x3State.InProgress -> null
    }

    gameOverDialogInfo?.let { info ->
        var isDialogVisible by remember { mutableStateOf(true) }

        if (isDialogVisible) {
            VictoryDialog(
                winner = info.winner,
                victoryCount = info.count,
                onDismiss = { isDialogVisible = false },
                onRestart = {
                    isDialogVisible = false
                    onRestartClick()
                },
            )
        }
    }
}

private data class GameOverDialogInfo(val winner: Player?, val count: Int)

@Composable
private fun PlayerAndScoreInformation(player: Player, gameMode: GameMode, wins: Int) {
    val glowingColor = if (player == Player.X) {
        TicTacToeColors.Player1.accentDark
    } else {
        TicTacToeColors.Player2.mainDark
    }
    GlowingCard(
        glowingColor = glowingColor,
        glowingRadius = 8.dp,
        containerColor = MaterialTheme.colorScheme.surfaceBright,
        cornersRadius = 16.dp,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            val playerNumber = if (player == Player.X) 1 else 2
            val playerTitle = stringResource(
                id = R.string.player_name_on_information_card,
                playerNumber,
            )

            when (player) {
                Player.X -> {
                    PlayerOneIcon(
                        modifier = Modifier
                            .size(36.dp)
                            .offset(x = (-2).dp),
                    )
                }

                Player.O -> {
                    OpponentIcon(
                        modifier = Modifier
                            .height(36.dp)
                            .offset(x = (-4).dp),
                        gameMode = gameMode,
                        tinyBadge = true,
                    )
                }
            }

            Spacer(modifier = Modifier.size(24.dp))

            Text(
                modifier = Modifier,
                text = stringResource(
                    id = if (player == Player.X) {
                        R.string.x_mark_in_grid
                    } else {
                        R.string.o_mark_in_grid
                    },
                ),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 28.sp,
                ),
                color = if (player == Player.X) {
                    TicTacToeColors.Player1.main
                } else {
                    TicTacToeColors.Player2.main
                },
            )

            Spacer(modifier = Modifier.size(16.dp))

            Text(
                modifier = Modifier,
                text = playerTitle,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.size(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .offset(y = (-4).dp),
                    painter = painterResource(id = R.drawable.winner_icon),
                    contentDescription = playerTitle,
                )

                Spacer(modifier = Modifier.size(4.dp))

                Text(
                    modifier = Modifier,
                    text = stringResource(id = R.string.win_count_label, wins),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                // Gold
                                Color(0xFFFFD700),
                                // Orange
                                Color(0xFFFFA500),
                                // GoldenRod
                                Color(0xFFDAA520),
                                // LimeGreen (subtle green touch)
                                Color(0xFF32CD32),
                            ),
                        ),
                    ),
                )
            }
        }
    }
}

@Composable
private fun TicTacToe3x3Grid(
    cells: List<Grid3x3Cell>,
    modifier: Modifier = Modifier,
    onClick: (Coordinate) -> Unit,
) {
    Column(modifier = modifier) {
        for (row in 0 until 3) {
            Row {
                for (col in 0 until 3) {
                    val cellIndex = row * 3 + col
                    val cell = cells[cellIndex]
                    TicTacToe3x3Cell(player = cell.value) {
                        onClick(cell.coordinate)
                    }
                    if (col < 2) {
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
            }
            if (row < 2) {
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}

@Composable
private fun TicTacToe3x3Cell(player: Player?, onClick: () -> Unit) {
    val backgroundColor = when (player) {
        Player.X -> TicTacToeColors.Player1.accentDark
        Player.O -> TicTacToeColors.Player2.mainDark
        null -> MaterialTheme.colorScheme.onBackground
    }
    ClickableGlowingCard(
        glowingColor = backgroundColor,
        glowingRadius = if (player != null) 8.dp else 2.dp,
        containerColor = backgroundColor,
        cornersRadius = 2.dp,
        onClick = onClick,
    ) {
        Box(
            modifier = Modifier.size(100.dp),
            contentAlignment = Alignment.Center,
        ) {
            when (player) {
                Player.X -> Text(
                    modifier = Modifier.offset(x = 4.dp, y = (4).dp),
                    text = stringResource(id = R.string.x_mark_in_grid),
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 60.sp,
                    ),
                    color = TicTacToeColors.Player1.main,
                    textAlign = TextAlign.Center,
                )

                Player.O -> Text(
                    modifier = Modifier.offset(x = 4.dp, y = (4).dp),
                    text = stringResource(id = R.string.o_mark_in_grid),
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 60.sp,
                    ),
                    color = TicTacToeColors.Player2.main,
                )

                null -> {
                    /* Empty cell, no content */
                }
            }
        }
    }
}

@Composable
fun GameScreenFABButton(
    onUndoClick: () -> Unit,
    onRestartClick: () -> Unit,
    onGoHomeClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = 22.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        FloatingActionButton(
            contentColor = MaterialTheme.colorScheme.background,
            containerColor = MaterialTheme.colorScheme.onBackground,
            onClick = onGoHomeClick,
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.home_icon),
                contentDescription = stringResource(
                    id = R.string.go_home_icon_desc,
                ),
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        FloatingActionButton(
            contentColor = MaterialTheme.colorScheme.background,
            containerColor = MaterialTheme.colorScheme.onBackground,
            onClick = onUndoClick,
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = R.drawable.undo_icon),
                contentDescription = stringResource(
                    id = R.string.undo_button_desc,
                ),
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        FloatingActionButton(
            contentColor = MaterialTheme.colorScheme.background,
            containerColor = MaterialTheme.colorScheme.onBackground,
            onClick = onRestartClick,
        ) {
            Icon(
                modifier = Modifier.size(34.dp),
                painter = painterResource(id = R.drawable.restart_icon),
                contentDescription = stringResource(
                    id = R.string.restart_button_desc,
                ),
            )
        }
    }
}

@Preview
@Composable
fun PreviewGameScreen() {
    TicTacToeTheme {
        var game by remember { mutableStateOf(TicTacToe3x3()) }
        game = (
            game.markCell(
                coordinate = Coordinate.of(
                    0,
                    0,
                ),
            ) as TicTacToe3x3MarkResult.Success
            ).updatedTicTacToe
        game = (
            game.markCell(
                coordinate = Coordinate.of(
                    2,
                    0,
                ),
            ) as TicTacToe3x3MarkResult.Success
            ).updatedTicTacToe
        Scaffold(
            floatingActionButton = {
                GameScreenFABButton(onUndoClick = {}, onRestartClick = {}, onGoHomeClick = {})
            },
        ) { paddingValues ->
            Surface(
                modifier = Modifier.padding(paddingValues),
            ) {
                GameScreen(
                    game = game,
                    xTotalGamesWon = 0,
                    oTotalGamesWon = 0,
                    totalDraws = 0,
                    gameMode = GameMode.VS_IMPOSSIBLE_BOT,
                    onRestartClick = {},
                    onCellClick = {
                        val result = game.markCell(it)
                        if (result is TicTacToe3x3MarkResult.Success) {
                            game = result.updatedTicTacToe
                        }
                    },
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewVictoryDialogO() {
    TicTacToeTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                VictoryDialog(winner = Player.O, victoryCount = 1, onDismiss = {}, onRestart = {})
            }
        }
    }
}

@Preview
@Composable
fun PreviewVictoryDialogX() {
    TicTacToeTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                VictoryDialog(winner = Player.X, victoryCount = 1, onDismiss = {}, onRestart = {})
            }
        }
    }
}

@Preview
@Composable
fun PreviewVictoryDialogDraw() {
    TicTacToeTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                VictoryDialog(winner = null, victoryCount = 1, onDismiss = {}, onRestart = {})
            }
        }
    }
}
