package ui.theaters

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.compose.theme.*
import core.compose.utils.getColorScheme

@Composable
fun TheaterTab(
    selectedItemIndex: Int,
    items: List<String>,
    modifier: Modifier = Modifier,
    tabWidth: Dp = 150.dp,
    onClick: (index: Int) -> Unit,
) {
    /* animateDpAsState is used to animate the tab indicator offset when the selected tab is changed */
    val indicatorOffset: Dp by animateDpAsState(
        targetValue = tabWidth * selectedItemIndex,
        animationSpec = tween(easing = LinearEasing),
    )

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(if(!isSystemInDarkTheme()) md_theme_dark_background else md_theme_light_background)
            .height(intrinsicSize = IntrinsicSize.Min),
    ) {
        TheaterTabIndicator(
            indicatorWidth = tabWidth,
            indicatorOffset = indicatorOffset,
            indicatorColor = if(!isSystemInDarkTheme()) currentTheme.first.getColorScheme().primaryContainer else currentTheme.second.getColorScheme().primaryContainer,
        )

        Row(
            modifier = Modifier.clip(CircleShape),
            horizontalArrangement = Arrangement.Center,
        ) {
            items.mapIndexed { index, text ->
                val isSelected = index == selectedItemIndex
                TheaterTabItem(
                    isSelected = isSelected,
                    onClick = { onClick(index) },
                    tabWidth = tabWidth,
                    text = text,
                )
            }
        }
    }
}