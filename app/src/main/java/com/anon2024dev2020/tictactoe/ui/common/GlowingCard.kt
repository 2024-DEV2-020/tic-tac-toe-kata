package com.anon2024dev2020.tictactoe.ui.common

import android.graphics.Paint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.anon2024dev2020.tictactoe.ui.theme.TicTacToeTheme

// credit: https://github.com/lofcoding/ComposeGlowingCard/blob/master/app/src/main/java/com/loc/composeglowingcard/GlowingCard.kt
@Composable
fun GlowingCard(
    glowingColor: Color,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.White,
    cornersRadius: Dp = 0.dp,
    glowingRadius: Dp = 20.dp,
    xShifting: Dp = 0.dp,
    yShifting: Dp = 0.dp,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .drawBehind {
                val canvasSize = size
                drawContext.canvas.nativeCanvas.apply {
                    drawRoundRect(
                        // Left coordinate of the rectangle
                        0f,
                        // Top coordinate of the rectangle
                        0f,
                        // Right coordinate of the rectangle (width of the canvas)
                        canvasSize.width,
                        // Bottom coordinate of the rectangle (height of the canvas)
                        canvasSize.height,
                        // X-axis corner radius in pixels
                        cornersRadius.toPx(),
                        // Y-axis corner radius in pixels
                        cornersRadius.toPx(),
                        Paint().apply {
                            color = containerColor.toArgb()
                            isAntiAlias = true
                            setShadowLayer(
                                glowingRadius.toPx(),
                                xShifting.toPx(),
                                yShifting.toPx(),
                                glowingColor.copy(alpha = 0.85f).toArgb(),
                            )
                        },
                    )
                }
            },
    ) {
        content()
    }
}

// ///////////////////////////////////////////////////////////////////

@Composable
fun ClickableGlowingCard(
    glowingColor: Color,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.White,
    cornersRadius: Dp = 0.dp,
    glowingRadius: Dp = 20.dp,
    xShifting: Dp = 0.dp,
    yShifting: Dp = 0.dp,
    onClick: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .drawBehind {
                val canvasSize = size
                drawContext.canvas.nativeCanvas.apply {
                    drawRoundRect(
                        // Left coordinate of the rectangle
                        0f,
                        // Top coordinate of the rectangle
                        0f,
                        // Right coordinate of the rectangle (width of the canvas)
                        canvasSize.width,
                        // Bottom coordinate of the rectangle (height of the canvas)
                        canvasSize.height,
                        // X-axis corner radius in pixels
                        cornersRadius.toPx(),
                        // Y-axis corner radius in pixels
                        cornersRadius.toPx(),
                        Paint().apply {
                            color = containerColor.toArgb()
                            isAntiAlias = true
                            setShadowLayer(
                                glowingRadius.toPx(),
                                xShifting.toPx(),
                                yShifting.toPx(),
                                glowingColor.copy(alpha = 0.85f).toArgb(),
                            )
                        },
                    )
                }
            },
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(cornersRadius))
                .clickable { onClick() },
        ) {
            content()
        }
    }
}

@Preview
@Composable
fun PreviewGlowingCard() {
    TicTacToeTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                GlowingCard(glowingColor = Color.Yellow) {
                    Text(
                        modifier = Modifier,
                        text = "test",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }
        }
    }
}
