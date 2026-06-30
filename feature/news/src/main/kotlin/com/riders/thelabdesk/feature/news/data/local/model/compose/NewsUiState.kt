package com.riders.thelabdesk.feature.news.data.local.model.compose

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.riders.thelabdesk.feature.news.data.local.model.NewsModel

@Stable
@Immutable
sealed class NewsUiState {

    @Stable
    @Immutable
    data class Success(val data: List<NewsModel>) : NewsUiState()

    @Stable
    @Immutable
    data class Error(val message: String) : NewsUiState()

    @Stable
    @Immutable
    data object Loading : NewsUiState()

    @Stable
    @Immutable
    data object None : NewsUiState()
}