package data.local.model.compose

import androidx.compose.runtime.Stable

@Stable
sealed class TheatersUiState {
    data object Loading : TheatersUiState()
    data object Success : TheatersUiState()
    data class Error(val message: String, val throwable: Throwable? = null) : TheatersUiState()
}