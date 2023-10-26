package data.local.model.compose

import data.remote.dto.tmdb.TMDBMovieResponse

sealed class MoviesUiState {
    data class Success(val response: TMDBMovieResponse) : MoviesUiState()
    data class Error(val message: String) : MoviesUiState()
    data object None : MoviesUiState()
}