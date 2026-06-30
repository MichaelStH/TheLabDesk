package com.riders.thelabdesk.feature.theaters.data.local.compose

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
@Immutable
sealed class TMDBViewState {
    @Stable
    @Immutable
    data object Movies : TMDBViewState()

    @Stable
    @Immutable
    data object TvShows : TMDBViewState()
}