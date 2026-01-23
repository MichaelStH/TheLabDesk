package com.riders.thelabdesk.core.ui.compose.component.dynamicisland

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.riders.thelabdesk.core.ui.compose.component.TheLabDeskSurface
import com.riders.thelabdesk.core.ui.compose.theme.TheLabDeskTheme
import com.riders.thelabdesk.core.ui.data.local.compose.IslandUiState

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

    TheLabDeskTheme {
        TheLabDeskSurface(modifier = Modifier) {
            TrailingContent(IslandUiState.CallState())
        }
    }
}