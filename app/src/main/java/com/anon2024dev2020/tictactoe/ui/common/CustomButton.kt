package com.anon2024dev2020.tictactoe.ui.common

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback

@Composable
fun CustomButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    val haptic = LocalHapticFeedback.current
    Button(
        colors =
        ButtonDefaults.buttonColors().copy(
            containerColor = MaterialTheme.colorScheme.onBackground,
            contentColor = MaterialTheme.colorScheme.background,
        ),
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            onClick()
        },
    ) {
        Text(text = text, style = MaterialTheme.typography.titleSmall)
    }
}
