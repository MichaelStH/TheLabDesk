package data.local.model.compose

import androidx.compose.runtime.Stable


@Stable
sealed class TheatersUiState {
    data object Movies : TheatersUiState()
    data object TvShows : TheatersUiState()
}