package com.anon2024dev2020.tictactoe.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.anon2024dev2020.tictactoe.R

val pressStart2P = FontFamily(
    Font(R.font.pressstart2p_regular),
)

// Set of Material typography styles to start with
val TicTacToeTypography =
    Typography(
        displayLarge = TextStyle(
            fontFamily = pressStart2P,
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp,
            lineHeight = 44.sp,
            letterSpacing = 0.sp,
        ),
        titleMedium = TextStyle(
            fontFamily = pressStart2P,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp,
        ),
        bodyLarge =
        TextStyle(
            fontFamily = pressStart2P,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
        ),
        bodySmall =
        TextStyle(
            fontFamily = pressStart2P,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
        ),
    )
