package com.anon2024dev2020.tictactoe.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
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
val Typography =
    Typography(
        displayLarge = TextStyle(
            fontFamily = pressStart2P,
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp,
            lineHeight = 44.sp,
            letterSpacing = 0.sp,
            shadow = Shadow(color = Color.Yellow, offset = Offset(x = 3F, y = 3F), blurRadius = 3f),
        ),
        bodyLarge =
        TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
        ),
        titleMedium = TextStyle(
            fontFamily = pressStart2P,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp,
        ),
        /* Other default text styles to override
        titleLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp
        ),
        labelSmall = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        )
         */
    )
