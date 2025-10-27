package data.local.model.compose

import androidx.compose.runtime.Stable

@Stable
sealed class TMDBViewState {
    @Stable
    data object Movies : TMDBViewState()

    @Stable
    data object TvShows : TMDBViewState()
}