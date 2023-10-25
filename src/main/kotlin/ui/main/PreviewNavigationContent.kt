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
import ui.home.HomeViewModel
import ui.news.News
import ui.news.NewsViewModel
import ui.settings.SettingsContent
import ui.theaters.Theaters
import ui.theaters.TheatersViewModel


//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@Composable
fun NavigationContent(
    viewModel: MainViewModel,
    homeViewModel: HomeViewModel,
    newsViewModel: NewsViewModel,
    theatersViewModel: TheatersViewModel
) {
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

            Box(modifier = Modifier, contentAlignment = Alignment.TopStart) {
                when (currentNavigation) {
                    is NavigationUiState.Home -> {
                        Home(homeViewModel)
                    }

                    is NavigationUiState.News -> {
                        newsViewModel.fetchNews()
                        News(newsViewModel)
                    }

                    is NavigationUiState.Theaters -> {
                        theatersViewModel.fetchMovies()
                        Theaters(theatersViewModel)
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
    val homeViewModel = HomeViewModel()
    val newsViewModel = NewsViewModel(AppModule.injectDependencies())
    val theatersViewModel = TheatersViewModel(AppModule.injectDependencies())

    viewModel.updateCurrentNavigationUiState(NavigationUiState.Settings)

    TheLabDeskTheme {
        NavigationContent(viewModel, homeViewModel, newsViewModel, theatersViewModel)
    }
}
