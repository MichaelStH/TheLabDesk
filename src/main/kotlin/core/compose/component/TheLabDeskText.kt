package core.compose.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import core.compose.theme.isSystemInDarkTheme

@Composable
fun TheLabDeskText(modifier: Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text,
        color = if (!isSystemInDarkTheme()) Color.Black else Color.White
    )
}

@Composable
fun TheLabDeskText(modifier: Modifier, text: String, color: Color) {
    Text(
        modifier = modifier,
        text = text,
        color = color
    )
}

@Composable
fun TheLabDeskText(modifier: Modifier, text: String, maxLines: Int = 2, style: TextStyle) {
    Text(
        modifier = modifier,
        text = text,
        style = style.copy(color = if (!isSystemInDarkTheme()) Color.Black else Color.White),
        maxLines = maxLines
    )
}