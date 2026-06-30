package com.riders.thelabdesk.feature.theaters.data.local.compose

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
@Immutable
sealed class TheatersUiState {
    @Stable
    @Immutable
    data object Loading : TheatersUiState()

    @Stable
    @Immutable
    data object Success : TheatersUiState()

    @Stable
    @Immutable
    data class Error(val message: String, val throwable: Throwable? = null) : TheatersUiState()
}