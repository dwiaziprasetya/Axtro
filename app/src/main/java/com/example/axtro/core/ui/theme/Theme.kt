package com.example.axtro.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = primary,
    onPrimary = Color.White,
    secondary = secondary,
    tertiary = tertiary,
    background = dark,
)

private val LightColorScheme = lightColorScheme(
    primary = primary,
    onPrimary = Color.Black,
    secondary = secondary,
    tertiary = tertiary,
    background = light,
)

@Composable
fun ListifyTheme(
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    MaterialTheme(
      colorScheme = LightColorScheme,
      typography = Typography,
      content = content
    )
}