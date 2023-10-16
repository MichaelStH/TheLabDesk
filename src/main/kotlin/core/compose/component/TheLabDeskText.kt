package core.compose.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import core.compose.theme.isSystemInDarkTheme

@Composable
fun TheLabDeskText(modifier: Modifier, text: String, style: TextStyle = TextStyle()) {
    Text(
        modifier = modifier,
        text = text,
        style = style,
        color = if (!isSystemInDarkTheme()) Color.Black else Color.White
    )
}