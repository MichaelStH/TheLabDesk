package ui.theaters

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.compose.component.TheLabDeskCard
import core.compose.component.TheLabDeskText
import core.compose.theme.TheLabDeskTheme
import core.compose.theme.Typography
import core.compose.utils.AsyncBitmapImageFromNetwork
import data.local.model.compose.MoviesUiState
import data.local.model.tmdb.MovieModel
import kotlinx.coroutines.launch
import utils.Constants

/**
 * Scrollable row section of TMDB items
 *
 * @param viewModel instance of ViewModel use to set Dark Mode
 * @param title represents the title of the section
 * @param list represents the list we want to display (with [MovieModel] items)
 */
@Composable
private fun TMDBLazyRowSection(viewModel: TheatersViewModel, title: String, list: List<MovieModel>) {
    // Remember a CoroutineScope to be able to launch
    val coroutineScope = rememberCoroutineScope()
    val lazyRowListState = rememberLazyListState()

    TheLabDeskTheme(viewModel.isDarkMode) {
        Column(modifier = Modifier.heightIn(0.dp, 450.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            TheLabDeskText(
                modifier = Modifier,
                text = title,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.W600)
            )

            if (viewModel.trendingMovieList.toList().isEmpty()) {
                CircularProgressIndicator()
            } else {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .draggable(
                            orientation = Orientation.Horizontal,
                            state = rememberDraggableState { delta ->
                                coroutineScope.launch {
                                    lazyRowListState.scrollBy(-delta)
                                }
                            },
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    state = lazyRowListState
                ) {
                    itemsIndexed(items = list) { index, item ->
                        TheatersItem(viewModel, item) {
                            coroutineScope.launch {
                                // Animate scroll to the index-th item
                                lazyRowListState.animateScrollToItem(index = index)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Header(viewModel: TheatersViewModel, movie: MovieModel) {
    val backdropUrl =
        "${Constants.BASE_URL_TMDB_IMAGE_W_500_ENDPOINT}${movie.backdropPath}"
    val poster =
        "${Constants.BASE_URL_TMDB_IMAGE_W_500_ENDPOINT}${movie.poster}"

    TheLabDeskTheme(viewModel.isDarkMode) {
        TMDBHeader(
            viewModel = viewModel,
            "Trending right now",
            title = movie.originalTitle.toString(),
            backdropUrl = backdropUrl,
            posterUrl = poster
        )
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
fun MoviesContent(viewModel: TheatersViewModel) {
    // Remember a CoroutineScope to be able to launch
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()
    val movieUiState by viewModel.movieUiState.collectAsState()

    TheLabDeskTheme(viewModel.isDarkMode) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (movieUiState) {
                is MoviesUiState.Success -> {
                    if (!viewModel.isStaggeredMode) {
                        LazyColumn(
                            modifier = Modifier
                                .width(this.maxWidth)
                                .heightIn(0.dp, this.maxHeight)
                                .defaultMinSize(minHeight = 1.dp)
                                .padding(10.dp),
                            state = lazyListState,
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy(24.dp),
                        ) {
                            item {
                                if (viewModel.trendingMovieList.isNotEmpty()) {
                                    val movie = viewModel.trendingMovieList.toList().first()
                                    val backdropUrl =
                                        "${Constants.BASE_URL_TMDB_IMAGE_W_500_ENDPOINT}${movie.backdropPath}"
                                    val poster =
                                        "${Constants.BASE_URL_TMDB_IMAGE_W_500_ENDPOINT}${movie.poster}"

                                    TMDBHeader(
                                        viewModel = viewModel,
                                        "Trending right now",
                                        title = movie.originalTitle.toString(),
                                        backdropUrl = backdropUrl,
                                        posterUrl = poster
                                    )
                                }
                            }

                            item {
                                // Trending Movies
                                TMDBLazyRowSection(
                                    viewModel,
                                    Constants.TITLE_TRENDING_MOVIES,
                                    viewModel.trendingMovieList.toList()
                                )
                            }
                            item {
                                // Popular Movies
                                TMDBLazyRowSection(
                                    viewModel,
                                    Constants.TITLE_POPULAR_MOVIES,
                                    viewModel.popularMovieList.toList()
                                )
                            }
                            item {
                                // Upcoming Movies
                                TMDBLazyRowSection(
                                    viewModel,
                                    Constants.TITLE_UPCOMING_MOVIES,
                                    viewModel.upcomingMovieList.toList()
                                )
                            }
                        }
                    } else {
                        LazyVerticalStaggeredGrid(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(0.dp, this@BoxWithConstraints.maxHeight)
                                .defaultMinSize(minHeight = 1.dp)
                                .padding(10.dp),
                            columns = StaggeredGridCells.Adaptive(190.dp),
                            state = lazyStaggeredGridState,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalItemSpacing = 12.dp
                        ) {
                            item(
                                // Replace "maxCurrentLineSpan" with the number of spans this item should take.
                                // Use "maxCurrentLineSpan" if you want to take full width.
                                span = StaggeredGridItemSpan.FullLine
                            ) {
                                if (viewModel.trendingMovieList.isNotEmpty()) {
                                    Header(viewModel, viewModel.trendingMovieList.toList().first())
                                }
                            }

                            itemsIndexed(items = viewModel.trendingMovieList.toList()) { index, item ->
                                TheatersItem(viewModel, item) {
                                    coroutineScope.launch {
                                        // Animate scroll to the index-th item
                                        lazyListState.animateScrollToItem(index = index)
                                    }
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