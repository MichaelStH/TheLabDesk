package core.compose.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import core.compose.theme.TheLabDeskTheme
import data.local.model.compose.IslandUiState
import di.AppModule
import ui.main.MainViewModel

@Composable
fun TrailingContent(state: IslandUiState) {
    AnimatedVisibility(
        modifier = Modifier.fillMaxHeight(),
        visible = state.hasTrailingContent,
        enter = fadeIn(animationSpec = tween(300, 300))
    ) {
        Box(
            modifier = Modifier.width(state.trailingContentSize),
            contentAlignment = Alignment.Center,
        ) {
            when (state) {
                is IslandUiState.CallState -> {
                    CallWaveform()
                }

                else -> {}
            }
        }
    }
}


@Preview
@Composable
private fun PreviewTrailingContent() {
    val viewModel = MainViewModel(AppModule.injectDependencies())
    viewModel.updateDarkMode(true)

    TheLabDeskTheme(viewModel.isDarkMode) {
        TheLabDeskSurface(modifier = Modifier) {
            TrailingContent(IslandUiState.CallState())
        }
    }
}