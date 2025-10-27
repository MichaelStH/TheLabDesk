package data.local.model.compose

import androidx.compose.runtime.Stable
import data.remote.dto.NewsDto


@Stable
sealed class NewsUiState {

    @Stable
    data class Success(val data: List<NewsDto>) : NewsUiState()

    @Stable
    data class Error(val message: String) : NewsUiState()

    @Stable
    data object Loading : NewsUiState()

    @Stable
    data object None : NewsUiState()
}