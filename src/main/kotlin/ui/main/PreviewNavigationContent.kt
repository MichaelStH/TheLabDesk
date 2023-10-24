package ui.main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.compose.component.TheLabDeskText
import core.compose.theme.TheLabDeskTheme
import data.local.model.compose.NavigationUiState
import di.AppModule
import ui.home.Home
import ui.news.News
import ui.settings.SettingsContent
import ui.theaters.Theaters
import viewmodel.MainViewModel


//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@Composable
fun NavigationContent(viewModel: MainViewModel) {
    val currentNavigation by viewModel.currentNavigationUiState.collectAsState()

    TheLabDeskTheme(viewModel.isDarkMode) {
        Column(
            modifier = Modifier.fillMaxSize().padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TheLabDeskText(
                modifier = Modifier,
                text = currentNavigation.javaClass.simpleName,
                style = TextStyle(fontWeight = FontWeight.W600, fontSize = 26.sp)
            )

            Box(
                modifier = Modifier,
                //color = Color.LightGray,
                // shape = RoundedCornerShape(35.dp),
                contentAlignment = Alignment.TopStart
            ) {
                when (currentNavigation) {
                    is NavigationUiState.Home -> {
                        Home(viewModel)
                    }

                    is NavigationUiState.News -> {
                        viewModel.fetchNews()
                        News(viewModel)
                    }
                    is NavigationUiState.Theaters -> {
                        viewModel.fetchMovies()
                        Theaters(viewModel)
                    }

                    is NavigationUiState.Settings -> {
                        SettingsContent(viewModel)
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
private fun PreviewNavigationContent() {
    val viewModel = MainViewModel(AppModule.injectDependencies())
    viewModel.updateCurrentNavigationUiState(NavigationUiState.Settings)
    TheLabDeskTheme {
        NavigationContent(viewModel = viewModel)
    }
}
