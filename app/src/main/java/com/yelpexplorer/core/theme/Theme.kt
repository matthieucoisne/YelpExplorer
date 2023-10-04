package com.yelpexplorer.core.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val darkColorScheme = darkColorScheme(
    primary = Red900,
    onPrimary = Color.White,
    surface = Color.DarkGray,
    surfaceTint = Color.DarkGray,
    background = Charcoal,
)

private val lightColorScheme = lightColorScheme(
    primary = Red900,
    onPrimary = Color.White,
    surface = WhiteSmoke,
    surfaceTint = WhiteSmoke,
    background = Color.White
)

@Composable
fun YelpExplorerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = DarkRed.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
//        shapes = MaterialTheme.shapes, // TODO should we use shapes?
        content = content
    )
}
