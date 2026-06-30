package ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.riders.thelabdesk.core.common.utils.SystemManager
import com.riders.thelabdesk.core.ui.base.UiEvent
import com.riders.thelabdesk.core.ui.compose.component.TheLabDeskIconTab
import com.riders.thelabdesk.core.ui.compose.component.TheLabDeskText
import com.riders.thelabdesk.core.ui.compose.theme.TheLabDeskTheme
import com.riders.thelabdesk.feature.browser.BrowserContent
import com.riders.thelabdesk.feature.browser.BrowserViewModel
import com.riders.thelabdesk.feature.home.ui.Home
import com.riders.thelabdesk.feature.home.ui.HomeViewModel
import com.riders.thelabdesk.feature.news.ui.News
import com.riders.thelabdesk.feature.news.ui.NewsViewModel
import com.riders.thelabdesk.feature.settings.SettingsContent
import com.riders.thelabdesk.feature.theaters.ui.TheaterTab
import com.riders.thelabdesk.feature.theaters.ui.Theaters
import com.riders.thelabdesk.ui.TheLabDeskViewModel
import com.riders.thelabdesk.feature.theaters.ui.TheatersViewModel
import com.riders.thelabdesk.core.domain.repository.PreviewRepository
import com.riders.thelabdesk.core.domain.usecase.NewsUseCase
import data.local.model.compose.NavigationUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
    viewModel: TheLabDeskViewModel,
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
                                items = listOf(Icons.AutoMirrored.Filled.List, Icons.Filled.Dashboard),
                                selectedItemIndex = theatersViewModel.tabIconSelected,
                                onClick = { theatersViewModel.updateTabIconSelected(it) },
                            )


                            // Tooltip
                            val tooltipState = rememberTooltipState()
                            val scope = rememberCoroutineScope()

                            TooltipBox(
                                state = tooltipState,
                                positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
                                tooltip = {
                                    Column {

                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            TheLabDeskText(
                                                modifier = Modifier,
                                                text = Constants.PLACEHOLDER_PROVIDED_BY
                                            )
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
                                    }

                                    // Android
                                    TextButton(
                                        onClick = {
                                            SystemManager.openInBrowser(Constants.URL_TMDB_WEBSITE)

                                            scope.launch {
                                                delay(1_500)
                                                tooltipState.dismiss()
                                            }
                                        }
                                    ) { Text("Learn More") }
                                }
                            ) {
                                IconButton(
                                    modifier = Modifier,
                                    onClick = { scope.launch { tooltipState.show() } }
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
                        SettingsContent(isDarkMode = viewModel.isDarkMode, uiEvent = { event ->
                            when (event) {
                                is UiEvent.OnUpdateDarkMode -> viewModel.updateDarkMode(event.isDarkMode)
                                else -> {}
                            }
                        })
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
    val viewModel = TheLabDeskViewModel(PreviewRepository)
    val homeViewModel = HomeViewModel()
    val newsViewModel = NewsViewModel(NewsUseCase(PreviewRepository))
    val theatersViewModel = TheatersViewModel(PreviewRepository)

    viewModel.updateCurrentNavigationUiState(NavigationUiState.Settings)

    TheLabDeskTheme {
        // NavigationContent(viewModel, homeViewModel, newsViewModel, theatersViewModel)
    }
}
