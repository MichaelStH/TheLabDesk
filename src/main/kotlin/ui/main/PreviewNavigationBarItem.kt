package ui.main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.compose.theme.TheLabDeskTheme
import core.compose.theme.md_theme_dark_primaryContainer
import data.local.model.compose.NavigationUiState
import di.AppModule
import viewmodel.MainViewModel


//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationBarItem(item: NavigationUiState, onNavigationClicked: (NavigationUiState) -> Unit) {
    val shape = RoundedCornerShape(14.dp)
    TheLabDeskTheme {
        Card(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            onClick = { onNavigationClicked(item) },
            colors = CardDefaults.cardColors(containerColor = if (item.selected) md_theme_dark_primaryContainer else Color.Transparent),
            shape = shape
        ) {
            Column(
                modifier = Modifier.padding(10.dp).background(Color.Transparent),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
            ) {
                Icon(
                    modifier = Modifier.size(60.dp).padding(8.dp), imageVector = item.icon, contentDescription = null
                )

                Text(
                    text = item.javaClass.simpleName,
                    style = MaterialTheme.typography.bodyMedium
                )
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
    val viewModel: MainViewModel = MainViewModel(AppModule.injectDependencies())
    MaterialTheme {
        NavigationBarItem(
            item = NavigationUiState.Settings,
            viewModel::updateNavigationItemSelected
        )
    }
}
