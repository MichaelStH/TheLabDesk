package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.compose.theme.TheLabDeskTheme
import data.local.model.compose.NavigationUiState
import di.AppModule
import viewmodel.MainViewModel


//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@Composable
fun NavigationContent(viewModel: MainViewModel) {
    val currentNavigation by viewModel.currentNavigationUiState.collectAsState()

    TheLabDeskTheme {
        Card(colors = CardDefaults.cardColors(containerColor = Color.LightGray), shape = RoundedCornerShape(35.dp)) {
            when (currentNavigation) {
                is NavigationUiState.Home -> {
                    Home(viewModel)
                }

                is NavigationUiState.News -> {
                    viewModel.fetchNews()
                    News(viewModel)
                }

                is NavigationUiState.Settings -> {
                    SettingsContent(viewModel)
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
private fun PreviewNavigationContent() {
    val viewModel: MainViewModel = MainViewModel(AppModule.injectDependencies())
    TheLabDeskTheme {
        NavigationContent(viewModel = viewModel)
    }
}
