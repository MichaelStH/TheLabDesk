package ui.theaters

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.compose.component.TheLabDeskText
import core.compose.theme.TheLabDeskTheme
import data.local.model.compose.MoviesUiState


@Composable
fun PopularTvShows(viewModel: TheatersViewModel) {
    val lazyListState = rememberLazyListState()
    TheLabDeskTheme(viewModel.isDarkMode) {

        Column(modifier = Modifier.heightIn(0.dp, 450.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            TheLabDeskText(modifier = Modifier, text = "Popular TV Shows")
        }
    }
}

@Composable
fun TrendingTvShows(viewModel: TheatersViewModel) {
    val lazyListState = rememberLazyListState()
    TheLabDeskTheme(viewModel.isDarkMode) {

        Column(modifier = Modifier.heightIn(0.dp, 450.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            TheLabDeskText(modifier = Modifier, text = "Trending TV Shows")
        }
    }
}


@Composable
fun TvShowsContent(viewModel: TheatersViewModel) {
    val lazyListState = rememberLazyListState()
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()
    val tvShowsUiState by viewModel.tvShowsUiState.collectAsState()

    TheLabDeskTheme(viewModel.isDarkMode) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (tvShowsUiState) {
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
                                Header(
                                    viewModel,
                                    (tvShowsUiState as MoviesUiState.Success).response.results.first()
                                )
                            }
                            item { PopularTvShows(viewModel) }
                            item { TrendingTvShows(viewModel) }
                        }
                    } else {
                        LazyVerticalStaggeredGrid(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(0.dp, this@BoxWithConstraints.maxHeight)
                                .defaultMinSize(minHeight = 1.dp),
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
                                Header(viewModel, (tvShowsUiState as MoviesUiState.Success).response.results.first())
                            }

                            items(items = (tvShowsUiState as MoviesUiState.Success).response.results) {
                                TheatersItem(viewModel, it)
                            }
                        }
                    }
                }

                is MoviesUiState.Error -> {
                    TheLabDeskText(modifier = Modifier, text = (tvShowsUiState as MoviesUiState.Error).message)
                }

                is MoviesUiState.None -> {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
