package ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.riders.thelabdesk.core.ui.compose.theme.TheLabDeskTheme
import com.riders.thelabdesk.feature.browser.BrowserViewModel
import com.riders.thelabdesk.feature.home.ui.HomeViewModel
import com.riders.thelabdesk.feature.news.ui.NewsViewModel
import com.riders.thelabdesk.ui.TheLabDeskViewModel
import com.riders.thelabdesk.feature.theaters.ui.TheatersViewModel
import com.riders.thelabdesk.core.domain.repository.PreviewRepository
import com.riders.thelabdesk.core.domain.usecase.NewsUseCase


//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@Composable
fun App(
    composeWindow: ComposeWindow,
    viewModel: TheLabDeskViewModel,
    homeViewModel: HomeViewModel,
    newsViewModel: NewsViewModel,
    browserViewModel: BrowserViewModel,
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
                Box(modifier = Modifier.fillMaxWidth().zIndex(2f), contentAlignment = Alignment.CenterStart) {
                    NavigationContent(
                        composeWindow,
                        viewModel,
                        homeViewModel,
                        newsViewModel,
                        browserViewModel,
                        theatersViewModel
                    )
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
    val viewModel = TheLabDeskViewModel(PreviewRepository)
    val homeViewModel = HomeViewModel()
    val newsViewModel = NewsViewModel(NewsUseCase(PreviewRepository))
    val theatersViewModel = TheatersViewModel(PreviewRepository)

    TheLabDeskTheme {
        //App(viewModel, homeViewModel, newsViewModel, theatersViewModel)
    }
}
