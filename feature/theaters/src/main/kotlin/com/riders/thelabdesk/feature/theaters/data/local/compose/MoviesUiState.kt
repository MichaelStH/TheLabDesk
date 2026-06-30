package com.riders.thelabdesk.feature.theaters.data.local.compose

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.riders.thelabdesk.feature.theaters.data.local.model.TMDBModelCompose

@Stable
@Immutable
sealed class MoviesUiState {
    @Stable
    @Immutable
    data class Success(val response: TMDBModelCompose) : MoviesUiState()

    @Stable
    @Immutable
    data class Error(val message: String) : MoviesUiState()

    @Stable
    @Immutable
    data object None : MoviesUiState()
}