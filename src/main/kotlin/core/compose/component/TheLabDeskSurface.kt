package core.compose.component

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import core.compose.theme.*

@Composable
fun TheLabDeskSurface(modifier: Modifier, content: @Composable () -> Unit) {
    val fromColor = if (!isSystemInDarkTheme()) md_theme_light_background else md_theme_dark_background
    val targetColor = if (isSystemInDarkTheme()) md_theme_light_background else md_theme_dark_background

    val animateColor = animateColor(fromColor, targetColor)

    val color = remember { Animatable(fromColor) }
    LaunchedEffect(isSystemInDarkTheme()) {
        color.animateTo(targetColor, animationSpec = tween(1000))
    }

    TheLabDeskTheme {
        Surface(
            modifier = modifier,
            color = if (!isSystemInDarkTheme()) md_theme_light_background else md_theme_dark_background
            //color = color.value
        ) { content() }
    }
}

@Composable
fun TheLabDeskSurface(modifier: Modifier, color: Color, content: @Composable () -> Unit) {
    TheLabDeskTheme {
        Surface(modifier = modifier, color = color) { content() }
    }
}
