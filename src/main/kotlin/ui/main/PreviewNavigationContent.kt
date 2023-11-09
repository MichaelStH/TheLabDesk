package ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.compose.component.TheLabDeskIconTab
import core.compose.component.TheLabDeskText
import core.compose.theme.TheLabDeskTheme
import core.compose.theme.isSystemInDarkTheme
import core.utils.SystemManager
import data.local.model.compose.NavigationUiState
import di.AppModule
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ui.browser.BrowserContent
import ui.browser.BrowserViewModel
import ui.home.Home
import ui.home.HomeViewModel
import ui.news.News
import ui.news.NewsViewModel
import ui.settings.SettingsContent
import ui.theaters.TheaterTab
import ui.theaters.Theaters
import ui.theaters.TheatersViewModel
import utils.Constants


//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationContent(
    composeWindow: ComposeWindow,
    viewModel: MainViewModel,
    homeViewModel: HomeViewModel,
    newsViewModel: NewsViewModel,
    browserViewModel: BrowserViewModel,
    theatersViewModel: TheatersViewModel
) {
    val currentNavigation by viewModel.currentNavigationUiState.collectAsState()

    TheLabDeskTheme(viewModel.isDarkMode) {
        Column(
            modifier = Modifier.fillMaxSize().padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row {
                    TheLabDeskText(
                        modifier = Modifier,
                        text = currentNavigation.javaClass.simpleName,
                        style = TextStyle(
                            fontWeight = FontWeight.W600,
                            fontSize = 26.sp,
                            color = if (!isSystemInDarkTheme()) Color.Black else Color.White
                        )
                    )

                    AnimatedVisibility(visible = currentNavigation is NavigationUiState.Theaters) {
                        Box(modifier = Modifier.heightIn(0.dp, 40.dp).padding(horizontal = 40.dp)) {
                            TheaterTab(
                                items = listOf("MOVIES", "TV SHOWS"),
                                selectedItemIndex = theatersViewModel.theaterTypeSelected,
                                onClick = { theatersViewModel.updateTheaterTypeSelected(it) },
                            )
                        }
                    }
                }


                AnimatedVisibility(visible = currentNavigation is NavigationUiState.Theaters) {
                    Box(modifier = Modifier.heightIn(0.dp, 40.dp)) {
                        Row(
                            modifier = Modifier.fillMaxHeight(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            // Visibility mode
                            TheLabDeskIconTab(
                                tabWidth = 42.dp,
                                items = listOf(Icons.Filled.List, Icons.Filled.Dashboard),
                                selectedItemIndex = theatersViewModel.tabIconSelected,
                                onClick = { theatersViewModel.updateTabIconSelected(it) },
                            )


                            // Tooltip
                            val tooltipState = remember { RichTooltipState() }
                            val scope = rememberCoroutineScope()

                            RichTooltipBox(
                                title = { },
                                action = {
                                    TextButton(
                                        onClick = {
                                            SystemManager.openInBrowser(Constants.URL_TMDB_WEBSITE)

                                            scope.launch {
                                                delay(1_500)
                                                tooltipState.dismiss()
                                            }
                                        }
                                    ) { Text("Learn More") }
                                },
                                text = {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        TheLabDeskText(modifier = Modifier, text = Constants.PLACEHOLDER_PROVIDED_BY)
                                        Image(
                                            modifier = Modifier
                                                .widthIn(36.dp, 72.dp)
                                                .height(40.dp)
                                                .clip(RoundedCornerShape(12.dp)),
                                            painter = painterResource(resourcePath = "images/tmdb_logo.png"),
                                            contentDescription = "TMDB logo",
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                },
                                tooltipState = tooltipState
                            ) {
                                IconButton(
                                    onClick = { scope.launch { tooltipState.show() } },
                                    modifier = Modifier.tooltipAnchor()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = "About icon",
                                        tint = if (!isSystemInDarkTheme()) Color.Black else Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Box(modifier = Modifier, contentAlignment = Alignment.TopStart) {
                when (currentNavigation) {
                    is NavigationUiState.Home -> {
                        Home(viewModel = homeViewModel)
                    }

                    is NavigationUiState.News -> {
                        News(viewModel = newsViewModel)
                    }

                    is NavigationUiState.Theaters -> {
                        Theaters(viewModel = theatersViewModel)
                    }

                    is NavigationUiState.WebView -> {
                        BrowserContent(composeWindow = composeWindow, viewModel = browserViewModel)
                    }

                    is NavigationUiState.Settings -> {
                        SettingsContent(viewModel = viewModel)
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
        // NavigationContent(viewModel, homeViewModel, newsViewModel, theatersViewModel)
    }
}
