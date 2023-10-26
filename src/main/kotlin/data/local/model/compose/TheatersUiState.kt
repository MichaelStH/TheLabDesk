package data.local.model.compose

sealed class TheatersUiState {
    data object Movies : TheatersUiState()
    data object TvShows : TheatersUiState()
}