package ui.news

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.compose.theme.TheLabDeskTheme
import data.local.model.compose.NewsUiState
import di.AppModule

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun News(viewModel: NewsViewModel) {
    val state = rememberLazyListState()

    val newsUiState by viewModel.newsUiState.collectAsState()

    TheLabDeskTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            when (newsUiState) {
                is NewsUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        state = state,
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        itemsIndexed(
                            items = (newsUiState as NewsUiState.Success).data,
                            key = { _, item -> item.id }
                        ) { index, item ->
                            NewsDataItem(modifier = Modifier.animateItemPlacement(), item = item)
                        }
                    }
                }

                else -> {
                    CircularProgressIndicator()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchNews()
    }
}

@Preview
@Composable
private fun PreviewNews() {
    TheLabDeskTheme {
        News(NewsViewModel(AppModule.injectDependencies()))
    }
}