package ui.theaters

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TheaterTabItem(isSelected: Boolean, onClick: () -> Unit, tabWidth: Dp, text: String) {
    val tabTextColor: Color by animateColorAsState(
        targetValue = if (isSelected) {
            Color.White
        } else {
            Color.Black
        },
        animationSpec = tween(easing = LinearEasing),
    )

    Text(
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .width(tabWidth)
            .padding(vertical = 8.dp, horizontal = 12.dp),
        text = text,
        color = tabTextColor,
        textAlign = TextAlign.Center,
    )
}