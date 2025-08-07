package com.bertiland.kori.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF0061A4),
    onPrimary = Color.White,
    secondary = Color(0xFF018786),
    onSecondary = Color.White,
    background = Color(0xFFFFFFFF),
    onBackground = Color.Black,
    surface = Color(0xFFF5F5F5),
    onSurface = Color.Black,
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF90CAF9),
    onPrimary = Color.Black,
    secondary = Color(0xFF03DAC6),
    onSecondary = Color.Black,
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,
)

@Composable
fun KoriTheme(
    darkTheme: Boolean = false, // tu peux ajouter un paramètre pour switcher thème clair/sombre
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        //typography = Typography(), // tu peux définir ta typo ici
        content = content
    )
}
