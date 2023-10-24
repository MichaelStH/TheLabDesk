package ui.theaters

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import core.compose.component.TheLabDeskCard
import core.compose.component.TheLabDeskText
import core.compose.theme.TheLabDeskTheme
import core.compose.utils.AsyncBitmapImageFromNetwork
import core.log.Timber
import data.local.model.compose.MoviesUiState
import di.AppModule
import utils.Constants
import viewmodel.MainViewModel


//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@Composable
fun Header(viewModel: MainViewModel) {
    TheLabDeskTheme(viewModel.isDarkMode) {

    }
}


@Composable
fun PopularMovies(viewModel: MainViewModel) {
    TheLabDeskTheme(viewModel.isDarkMode) {

    }
}


@Composable
fun UpcomingMovies(viewModel: MainViewModel) {
    TheLabDeskTheme(viewModel.isDarkMode) {

    }
}


@Composable
fun PopularTvShows(viewModel: MainViewModel) {
    TheLabDeskTheme(viewModel.isDarkMode) {

    }
}

@Composable
fun TrendingTvShows(viewModel: MainViewModel) {
    TheLabDeskTheme(viewModel.isDarkMode) {

    }
}


@Composable
fun Theaters(viewModel: MainViewModel) {
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()

    val movieUiState by viewModel.movieUiState.collectAsState()
    var bitmap: ImageBitmap? = null

    val netflixLogoURL = "https://image.tmdb.org/t/p/w500/wwemzKWzjKYJFfCeiB57q3r4Bcm.svg"

    TheLabDeskTheme(viewModel.isDarkMode) {
        Column(
            modifier = Modifier.fillMaxSize().padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Box(
                modifier = Modifier,
                //color = Color.LightGray,
                // shape = RoundedCornerShape(35.dp),
                contentAlignment = Alignment.Center
            ) {
                when (movieUiState) {
                    is MoviesUiState.Success -> {
                        val total = (movieUiState as MoviesUiState.Success).response.totalResults
                        LazyVerticalStaggeredGrid(
                            modifier = Modifier.fillMaxSize(),
                            columns = StaggeredGridCells.Adaptive(128.dp),
                            state = lazyStaggeredGridState,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalItemSpacing = 12.dp
                        ) {
                            items(items = (movieUiState as MoviesUiState.Success).response.results) {

                                val backdropUrl =
                                    "${Constants.BASE_URL_TMDB_IMAGE_W_500_ENDPOINT}${it.backdropPath}"
                                val poster =
                                    "${Constants.BASE_URL_TMDB_IMAGE_W_500_ENDPOINT}${it.poster}"

                                Timber.d(backdropUrl)
                                Timber.d(poster)

                                TheLabDeskCard(modifier = Modifier) {
                                    Column(modifier = Modifier) {
                                        AsyncBitmapImageFromNetwork(
                                            modifier = Modifier.widthIn(0.dp, 300.dp).heightIn(0.dp, 150.dp),
                                            url = backdropUrl
                                        )
                                    }
                                }
                            }
                        }
                    }

                    is MoviesUiState.Error -> {
                        TheLabDeskText(modifier = Modifier, text = (movieUiState as MoviesUiState.Error).message)
                    }

                    is MoviesUiState.None -> {
                        CircularProgressIndicator()
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
private fun PreviewTheatersContent() {
    val viewModel = MainViewModel(AppModule.injectDependencies())
    TheLabDeskTheme {
        Theaters(viewModel = viewModel)
    }
}
