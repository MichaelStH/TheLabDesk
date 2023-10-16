package core.compose.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.compose.theme.*

@Composable
fun TheLabDeskCard(
    modifier: Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    content: @Composable () -> Unit
) {
    TheLabDeskTheme {
        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(containerColor = if (!isSystemInDarkTheme()) md_theme_light_primaryContainer else md_theme_dark_background),
            shape = shape
        ) { content() }
    }
}

@Composable
fun TheLabDeskCard(
    modifier: Modifier,
    color: Color,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    content: @Composable () -> Unit
) {
    TheLabDeskTheme {
        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(containerColor = color),
            shape = shape
        ) { content() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TheLabDeskCard(
    modifier: Modifier,
    onClick: () -> Unit,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = if (!isSystemInDarkTheme()) md_theme_light_primaryContainer else md_theme_dark_primaryContainer),
        shape = shape
    ) { content() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TheLabDeskCard(
    modifier: Modifier,
    onClick: () -> Unit,
    color: Color,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = color),
        shape = shape
    ) { content() }
}