package data.local.model.compose

import androidx.compose.runtime.Stable

@Stable
sealed class TheatersUiState {
    @Stable
    data object Loading : TheatersUiState()

    @Stable
    data object Success : TheatersUiState()

    @Stable
    data class Error(val message: String, val throwable: Throwable? = null) : TheatersUiState()
}