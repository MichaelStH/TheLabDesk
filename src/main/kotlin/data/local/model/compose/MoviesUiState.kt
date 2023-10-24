package data.local.model.compose

import data.remote.dto.tmdb.TMDBResponse

sealed class MoviesUiState {
    data class Success(val response: TMDBResponse) : MoviesUiState()
    data class Error(val message: String) : MoviesUiState()
    data object None : MoviesUiState()
}