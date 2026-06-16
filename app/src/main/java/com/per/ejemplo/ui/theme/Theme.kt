package com.per.ejemplo.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Definir colores personalizados
val DarkGrayBackground = Color(0x883A3A3A) // Gris para modo oscuro
//val LightGrayBackground = Color(0xFFF5F5F5) // Gris claro para modo claro

// Esquema de colores para modo oscuro
private val DarkCombinacional= darkColorScheme(
    background = DarkGrayBackground,
    onBackground = Color(0xFFFEFDC3)
)

private val DarkColorScheme = darkColorScheme(
    primary = GrisDol,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = GrisDol

)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = Color(0xFFFFFEE1),
    onBackground = Color(0xFF422406)

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),*/

)

@Composable
fun EjemploTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkCombinacional
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun EjemploTheme2(darkTheme: Boolean, content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkCombinacional else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}