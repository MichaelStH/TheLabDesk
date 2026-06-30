package ui.main

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.riders.thelabdesk.core.domain.repository.PreviewRepository
import com.riders.thelabdesk.core.ui.compose.theme.TheLabDeskTheme
import com.riders.thelabdesk.core.ui.compose.theme.Typography
import com.riders.thelabdesk.core.ui.compose.theme.currentTheme
import com.riders.thelabdesk.core.ui.compose.utils.getColorScheme
import com.riders.thelabdesk.ui.TheLabDeskViewModel
import data.local.model.compose.NavigationUiState


//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationBarItem(
    viewModel: TheLabDeskViewModel,
    item: NavigationUiState,
    onNavigationClicked: (NavigationUiState) -> Unit
) {
    val shape = RoundedCornerShape(14.dp)
    val animatedHeight = animateDpAsState(targetValue = 30.dp, animationSpec = tween(300))

    TheLabDeskTheme {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(
                visible = item.selected,
                enter = fadeIn() + slideInVertically(animationSpec = tween(500), initialOffsetY = { -30 }),
                exit = fadeOut() + slideOutVertically(animationSpec = tween(500))
            ) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(animatedHeight.value)
                        .background(color = if (!isSystemInDarkTheme()) currentTheme.second.getColorScheme().primaryContainer else currentTheme.first.getColorScheme().primaryContainer)
                )
            }

            Card(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                onClick = { onNavigationClicked(item) },
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                shape = shape
            ) {
                Column(
                    modifier = Modifier.padding(10.dp).background(Color.Transparent),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = if (item.selected && !isSystemInDarkTheme()) currentTheme.second.getColorScheme().primaryContainer
                        else if (item.selected && isSystemInDarkTheme()) currentTheme.first.getColorScheme().primaryContainer
                        else {
                            if (!isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
                        }
                    )

                    if (!item.selected) {
                        Text(
                            text = item.javaClass.simpleName,
                            style = Typography.titleMedium,
                            color = if (!isSystemInDarkTheme()) Color.DarkGray else Color.LightGray,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

//////////////////////////////////////////////////
//
// PREVIEWS
//
//////////////////////////////////////////////////
@Preview
@Composable
private fun PreviewNavigationBarItem() {
    val viewModel = TheLabDeskViewModel(PreviewRepository)
    MaterialTheme {
        NavigationBarItem(
            viewModel = viewModel,
            item = NavigationUiState.Settings,
            viewModel::updateNavigationItemSelected
        )
    }
}
