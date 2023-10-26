package core.compose.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import core.compose.theme.Orange
import core.compose.theme.TheLabDeskTheme
import data.local.model.compose.IslandUiState
import di.AppModule
import ui.main.MainViewModel


@Composable
fun IslandBubbleContent(state: IslandUiState) {
    val width = state.bubbleContentSize.width
    val height = state.bubbleContentSize.height

    val scale = remember { Animatable(1.5f) }
    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                stiffness = Spring.StiffnessLow,
                dampingRatio = 0.35f,
            )
        )
    }

    Box(
        modifier = Modifier
            .width(width * scale.value)
            .height(height),
        contentAlignment = Alignment.Center,
    ) {

        var bubbleContent: @Composable () -> Unit by remember {
            mutableStateOf({})
        }
        LaunchedEffect(state, block = {
            when (state) {
                is IslandUiState.CallTimerState -> {
                    bubbleContent = {
                        Icon(
                            painter = painterResource(resourcePath = "images/timer.xml"),
                            contentDescription = null,
                            tint = Orange
                        )
                    }
                }

                else -> {}
            }
        })
        bubbleContent()
    }
}

@Preview
@Composable
fun IslandBubbleContent() {
    val viewModel = MainViewModel(AppModule.injectDependencies())
    viewModel.updateDarkMode(true)

    TheLabDeskTheme(viewModel.isDarkMode) {
        TheLabDeskSurface(modifier = Modifier) {
            IslandBubbleContent(IslandUiState.WelcomeState())
        }
    }
}