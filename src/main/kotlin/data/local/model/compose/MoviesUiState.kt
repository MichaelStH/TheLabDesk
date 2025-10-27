package data.local.model.compose

import androidx.compose.runtime.Stable
import data.remote.dto.tmdb.TMDBMovieResponse

@Stable
sealed class MoviesUiState {
    @Stable
    data class Success(val response: TMDBMovieResponse) : MoviesUiState()

    @Stable
    data class Error(val message: String) : MoviesUiState()

    @Stable
    data object None : MoviesUiState()
}