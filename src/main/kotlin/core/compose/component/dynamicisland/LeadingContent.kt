package core.compose.component.dynamicisland

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.text.TextStyle
import core.compose.component.TheLabDeskSurface
import core.compose.component.TheLabDeskText
import core.compose.theme.TheLabDeskTheme
import data.local.model.compose.IslandUiState
import di.AppModule
import ui.main.MainViewModel

@Preview
@Composable
fun LeadingContent(state: IslandUiState) {
    AnimatedVisibility(
        modifier = Modifier.fillMaxHeight(),
        visible = state.hasLeadingContent,
        enter = fadeIn(animationSpec = tween(300, 300))
    ) {
        Box(
            Modifier
                .width(state.leadingContentSize),
            contentAlignment = Alignment.Center,
        ) {
            when (state) {
                is IslandUiState.CallState -> {
                    TheLabDeskText(modifier = Modifier, text = "9:41", style = TextStyle(color = Green))
                }

                is IslandUiState.CallTimerState -> {
                    Icon(
                        imageVector = Icons.Rounded.Call,
                        contentDescription = null,
                        tint = Green,
                    )
                }

                else -> {}
            }
        }
    }
}

@Preview
@Composable
private fun LeadingContent() {
    val viewModel = MainViewModel(AppModule.injectDependencies())
    viewModel.updateDarkMode(true)

    TheLabDeskTheme(viewModel.isDarkMode) {
        TheLabDeskSurface(modifier = Modifier) {
            LeadingContent(IslandUiState.CallState())
        }
    }
}