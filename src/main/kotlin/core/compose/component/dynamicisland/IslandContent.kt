package  core.compose.component.dynamicisland

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.compose.component.TheLabDeskSurface
import ui.home.Welcome
import core.compose.theme.TheLabDeskTheme
import data.local.model.compose.IslandUiState
import di.AppModule
import ui.main.MainViewModel

@Composable
fun IslandContent(viewModel: MainViewModel, state: IslandUiState) {
    val width by animateDpAsState(
        targetValue = state.fullWidth,
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
            dampingRatio = .6f,
        )
    )

    val height by animateDpAsState(
        targetValue = state.contentSize.height,
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
            dampingRatio = .6f,
        )
    )

    Box(modifier = Modifier.width(width).height(height)) {
        AnimatedVisibility(
            visible = state.hasMainContent,
            enter = fadeIn(animationSpec = tween(300, 300))
        ) {
            Box(modifier = Modifier.size(state.contentSize)) {
                when (state) {
                    is IslandUiState.FaceUnlockState -> { FaceUnlock() }

                    else -> {}
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            LeadingContent(state)
            Box(Modifier.weight(1f)) {
                when (state) {
                    is IslandUiState.WelcomeState -> {
                        Welcome()
                    }

                    is IslandUiState.SearchState -> {
                        Search(viewModel)
                    }

                    else -> {}
                }
            }
            TrailingContent(state)
        }
    }
}


@Preview
@Composable
private fun PreviewIslandContent() {
    val viewModel = MainViewModel(AppModule.injectDependencies())
    viewModel.updateDarkMode(true)

    TheLabDeskTheme {
        TheLabDeskSurface(modifier = Modifier) {
            Column {
                Box(
                    modifier = Modifier.fillMaxWidth().heightIn(0.dp, 100.dp),
                    contentAlignment = Alignment.CenterStart
                ) {  DynamicIsland(viewModel, IslandUiState.WelcomeState()) }

                Box(
                    modifier = Modifier.fillMaxWidth().heightIn(0.dp, 100.dp),
                    contentAlignment = Alignment.CenterStart
                ) {  DynamicIsland(viewModel, IslandUiState.WelcomeState()) }
            }
        }
    }
}