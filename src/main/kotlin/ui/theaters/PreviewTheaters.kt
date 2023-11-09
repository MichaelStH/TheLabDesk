package ui.theaters

import androidx.compose.animation.AnimatedContent
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.compose.component.TheLabDeskCard
import core.compose.component.TheLabDeskText
import core.compose.theme.TheLabDeskTheme
import core.compose.theme.Typography
import core.compose.theme.isSystemInDarkTheme
import core.compose.utils.AsyncBitmapImageFromNetwork
import data.local.model.compose.TMDBViewState
import data.local.model.compose.TheatersUiState
import di.AppModule


//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@Composable
fun TMDBHeader(
    viewModel: TheatersViewModel,
    titlePlaceholder: String,
    title: String,
    backdropUrl: String,
    posterUrl: String
) {

    val gradientColors: List<Color> = if (!isSystemInDarkTheme()) listOf(
        Color.White,
        Color.White,
        Color.White,
        Color.Transparent
    ) else listOf(
        Color.Black,
        Color.Black,
        Color.Black,
        Color.Transparent
    )

    TheLabDeskTheme(viewModel.isDarkMode) {
        TheLabDeskCard(modifier = Modifier.fillMaxWidth().heightIn(0.dp, 300.dp)) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                // Image
                AsyncBitmapImageFromNetwork(
                    modifier = Modifier
                        .width(this.maxWidth / 3)
                        .height(this.maxHeight)
                        .align(Alignment.CenterEnd),
                    url = backdropUrl,
                    contentScale = ContentScale.Crop
                )

                // Gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = Brush.horizontalGradient(colors = gradientColors))
                        .align(Alignment.CenterStart)
                )

                Column(
                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 20.dp)
                ) {
                    Text(
                        modifier = Modifier,
                        text = titlePlaceholder,
                        style = Typography.bodyMedium
                    )

                    Text(
                        modifier = Modifier,
                        text = title,
                        style = Typography.titleLarge,
                        fontSize = 32.sp
                    )
                }
            }
        }
    }
}

@Composable
fun Theaters(viewModel: TheatersViewModel) {
    val theatersUiState by viewModel.theatersUiState.collectAsState()
    val tmdbViewState by viewModel.tmdbViewState.collectAsState()

    TheLabDeskTheme(viewModel.isDarkMode) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                modifier = Modifier.fillMaxSize(),
                targetState = theatersUiState,
                contentAlignment = Alignment.Center
            ) { targetState ->

                when (targetState) {
                    is TheatersUiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is TheatersUiState.Success -> {
                        when (tmdbViewState) {
                            is TMDBViewState.Movies -> {
                                MoviesContent(viewModel)
                            }

                            is TMDBViewState.TvShows -> {
                                TvShowsContent(viewModel)
                            }
                        }
                    }

                    is TheatersUiState.Error -> {
                        TheLabDeskText(modifier = Modifier, text = targetState.message)
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchTMDBData()
    }
}

//////////////////////////////////////////////////
//
// PREVIEWS
//
//////////////////////////////////////////////////
@Preview
@Composable
private fun PreviewTheatersContent() {
    val viewModel = TheatersViewModel(AppModule.injectDependencies())
    TheLabDeskTheme {
        Theaters(viewModel = viewModel)
    }
}
