package core.compose.component

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.compose.theme.currentTheme
import core.compose.theme.md_theme_dark_background
import core.compose.theme.md_theme_light_background
import core.compose.utils.getColorScheme

@Composable
fun TheLabDeskIconTab(
    selectedItemIndex: Int,
    items: List<ImageVector>,
    modifier: Modifier = Modifier,
    tabWidth: Dp = 50.dp,
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
            .background(if (!isSystemInDarkTheme()) md_theme_dark_background else md_theme_light_background)
            .height(intrinsicSize = IntrinsicSize.Min),
    ) {
        TheLabDeskIconIndicator(
            indicatorWidth = tabWidth,
            indicatorOffset = indicatorOffset,
            indicatorColor = if (!isSystemInDarkTheme()) currentTheme.first.getColorScheme().primaryContainer else currentTheme.second.getColorScheme().primaryContainer,
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.clip(CircleShape),
        ) {
            items.mapIndexed { index, icon ->
                val isSelected = index == selectedItemIndex
                TheLabDeskIconTabItem(
                    isSelected = isSelected,
                    onClick = { onClick(index) },
                    tabWidth = tabWidth,
                    icon = icon,
                )
            }
        }
    }
}