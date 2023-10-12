package data.local.model.compose

import data.remote.dto.NewsDto

sealed class NewsUiState {
    data class Success(val data: List<NewsDto>) : NewsUiState()
    data class Error(val message: String) : NewsUiState()
    data object Loading : NewsUiState()
    data object None : NewsUiState()
}