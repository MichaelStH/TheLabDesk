package ui.main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.unit.dp
import core.compose.theme.TheLabDeskTheme
import di.AppModule
import ui.home.HomeViewModel
import ui.news.NewsViewModel
import ui.theaters.TheatersViewModel


//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@Composable
@Preview
fun App(
    composeWindow: ComposeWindow,
    viewModel: MainViewModel,
    homeViewModel: HomeViewModel,
    newsViewModel: NewsViewModel,
    theatersViewModel: TheatersViewModel
) {
    TheLabDeskTheme(viewModel.isDarkMode) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(modifier = Modifier.width(100.dp).fillMaxHeight(), contentAlignment = Alignment.Center) {
                    NavigationBar(viewModel)
                }
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                    NavigationContent(composeWindow, viewModel, homeViewModel, newsViewModel, theatersViewModel)
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
private fun PreviewApp() {
    val viewModel = MainViewModel(AppModule.injectDependencies())
    val homeViewModel = HomeViewModel()
    val newsViewModel = NewsViewModel(AppModule.injectDependencies())
    val theatersViewModel = TheatersViewModel(AppModule.injectDependencies())

    TheLabDeskTheme {
        // App(viewModel, homeViewModel, newsViewModel, theatersViewModel)
    }
}
