package ui.theaters

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.compose.component.TheLabDeskCard
import core.compose.theme.TheLabDeskTheme
import core.compose.theme.Typography
import core.compose.utils.AsyncBitmapImageFromNetworkWithModifier
import data.local.model.compose.TheatersUiState
import data.local.model.tmdb.MovieModel
import data.local.model.tmdb.TvShowsModel
import data.remote.dto.tmdb.MovieDto
import di.AppModule
import utils.Constants


//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@Composable
fun MovieHeader(viewModel: TheatersViewModel, titlePlaceholder: String, item: MovieDto) {
    val backdropUrl =
        "${Constants.BASE_URL_TMDB_IMAGE_W_500_ENDPOINT}${item.backdropPath}"

    TheLabDeskTheme(viewModel.isDarkMode) {
        TheLabDeskCard(modifier = Modifier.fillMaxWidth().heightIn(0.dp, 300.dp)) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                // Image
                AsyncBitmapImageFromNetworkWithModifier(
                    modifier = Modifier
                        .width(this.maxWidth / 3)
                        .height(this.maxHeight)
                        .align(Alignment.CenterEnd),
                    url = backdropUrl
                )

                // Gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Black,
                                    Color.Black,
                                    Color.Black,
                                    Color.Transparent
                                )
                            )
                        )
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
                        text = item.originalTitle,
                        style = Typography.titleLarge,
                        fontSize = 32.sp
                    )
                }
            }
        }
    }
}

@Composable
fun Header(viewModel: TheatersViewModel, tvShowsModel: TvShowsModel) {
    val backdropUrl =
        "${Constants.BASE_URL_TMDB_IMAGE_W_500_ENDPOINT}${tvShowsModel.backdropPath}"
    val poster =
        "${Constants.BASE_URL_TMDB_IMAGE_W_500_ENDPOINT}${tvShowsModel.poster}"

    TheLabDeskTheme(viewModel.isDarkMode) {
        TheLabDeskCard(modifier = Modifier.fillMaxWidth().heightIn(0.dp, 300.dp)) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                // Image
                AsyncBitmapImageFromNetworkWithModifier(
                    modifier = Modifier
                        .width(this.maxWidth / 3)
                        .height(this.maxHeight)
                        .align(Alignment.CenterEnd),
                    url = backdropUrl
                )

                // Gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Black,
                                    Color.Black,
                                    Color.Black,
                                    Color.Transparent
                                )
                            )
                        )
                        .align(Alignment.CenterStart)
                )


                Column(
                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 20.dp)
                ) {
                    Text(
                        modifier = Modifier,
                        text = "Trending right now",
                        style = Typography.bodyMedium
                    )

                    Text(
                        modifier = Modifier,
                        text = tvShowsModel.originalTitle.toString(),
                        style = Typography.titleLarge,
                        fontSize = 32.sp
                    )
                }
            }
        }
    }
}


@Composable
fun Header(viewModel: TheatersViewModel, movie: MovieModel) {
    val backdropUrl =
        "${Constants.BASE_URL_TMDB_IMAGE_W_500_ENDPOINT}${movie.backdropPath}"
    val poster =
        "${Constants.BASE_URL_TMDB_IMAGE_W_500_ENDPOINT}${movie.poster}"

    TheLabDeskTheme(viewModel.isDarkMode) {
        TheLabDeskCard(modifier = Modifier.fillMaxWidth().heightIn(0.dp, 300.dp)) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                // Image
                AsyncBitmapImageFromNetworkWithModifier(
                    modifier = Modifier
                        .width(this.maxWidth / 3)
                        .height(this.maxHeight)
                        .align(Alignment.CenterEnd),
                    url = backdropUrl
                )

                // Gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Black,
                                    Color.Black,
                                    Color.Black,
                                    Color.Transparent
                                )
                            )
                        )
                        .align(Alignment.CenterStart)
                )


                Column(
                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 20.dp)
                ) {
                    Text(
                        modifier = Modifier,
                        text = "Trending right now",
                        style = Typography.bodyMedium
                    )

                    Text(
                        modifier = Modifier,
                        text = movie.originalTitle.toString(),
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

    TheLabDeskTheme(viewModel.isDarkMode) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (theatersUiState) {
                is TheatersUiState.Movies -> {
                    MoviesContent(viewModel)
                }

                is TheatersUiState.TvShows -> {
                    TvShowsContent(viewModel)
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
private fun PreviewTheatersContent() {
    val viewModel = TheatersViewModel(AppModule.injectDependencies())
    TheLabDeskTheme {
        Theaters(viewModel = viewModel)
    }
}
