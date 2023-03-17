package `in`.ktechnos.mp3player.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import android.annotation.SuppressLint


@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = Teal200,
    onPrimary = White,
    primaryVariant = TealA700,
    secondary = Teal200,
    error = RedErrorLight,
    background = Black2,
    onBackground = Gray300,
    surface = Black3,
    onSurface = Gray200,
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = Teal200,
    onPrimary = White,
    primaryVariant = TealA700,
    secondary = Teal200,
    onSecondary = Black1,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = BackGroundColor,
    onBackground = onBackGround,
    surface = SurfaceColor,
    onSurface = onSurface,
)

@Composable
fun HeadphonePlayerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = NunitoTypography,
        shapes = Shapes,
        content = content
    )
}

@Composable
fun MP3PlayerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}