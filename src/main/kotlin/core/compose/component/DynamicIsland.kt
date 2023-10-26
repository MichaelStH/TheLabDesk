package core.compose.component

import androidx.compose.animation.*
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import core.compose.theme.TheLabDeskTheme
import core.utils.DisplayManager
import data.local.model.compose.IslandUiState
import di.AppModule
import kotlinx.coroutines.launch
import ui.main.MainViewModel
import kotlin.math.roundToInt

@Composable
fun DynamicIsland(viewModel: MainViewModel, islandUiState: IslandUiState) {
    val scope = rememberCoroutineScope()

    val startPadding by animateDpAsState(
        targetValue = (DisplayManager.getScreenWidth().dp) - islandUiState.fullWidth / 2,
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
            dampingRatio = Spring.DampingRatioLowBouncy,
        ),
        label = ""
    )

    val shake = remember { Animatable(0f) }
    LaunchedEffect(islandUiState.hasBubbleContent) {
        scope.launch {
            shake.animateTo(15f)
            shake.animateTo(
                targetValue = 0f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow,
                )
            )
        }
    }

    MetaContainer(modifier = Modifier.height(200.dp)) {
        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .padding(start = startPadding)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
        ) {

            MetaEntity(
                modifier = Modifier
                    .offset { IntOffset(shake.value.roundToInt(), 0) }
                    .zIndex(10f),
                metaContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color.Black,
                                shape = RoundedCornerShape(35.dp)
                            )
                    )
                }
            ) { IslandContent(viewModel = viewModel, state = islandUiState) }

            AnimatedVisibility(
                visible = islandUiState.hasBubbleContent,
                modifier = Modifier.padding(start = 8.dp),
                enter = bubbleEnterTransition,
                exit = bubbleExitTransition,
            ) {
                MetaEntity(
                    metaContent = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Color.Black,
                                    shape = RoundedCornerShape(50.dp)
                                )
                        )
                    }
                ) { IslandBubbleContent(state = islandUiState) }
            }
        }
    }
}

@Composable
fun DynamicIsland(viewModel: MainViewModel, modifier: Modifier, islandUiState: IslandUiState) {
    MetaContainer(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
        ) {
            MetaEntity(
                modifier = Modifier.zIndex(10f),
                metaContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color.Black,
                                shape = RoundedCornerShape(35.dp)
                            )
                    )
                }
            ) { IslandContent(viewModel = viewModel, state = islandUiState) }

            AnimatedVisibility(
                visible = islandUiState.hasBubbleContent,
                modifier = Modifier.padding(start = 8.dp),
                enter = bubbleEnterTransition,
                exit = bubbleExitTransition,
            ) {
                MetaEntity(
                    metaContent = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Color.Black,
                                    shape = RoundedCornerShape(50.dp)
                                )
                        )
                    }
                ) { IslandBubbleContent(state = islandUiState) }
            }
        }
    }
}

private val bubbleEnterTransition = scaleIn(initialScale = .7f) + slideInHorizontally(
    animationSpec = spring(
        stiffness = Spring.StiffnessLow,
        dampingRatio = Spring.DampingRatioLowBouncy,
    )
) { -it }

private val bubbleExitTransition = scaleOut(targetScale = .7f) + slideOutHorizontally(
    animationSpec = spring(
        stiffness = Spring.StiffnessLow
    )
) { (-it * 1.2f).roundToInt() }

@Preview
@Composable
private fun PreviewDynamicIsland() {
    val viewModel = MainViewModel(AppModule.injectDependencies())
    viewModel.updateDarkMode(true)

    TheLabDeskTheme(viewModel.isDarkMode) {
        TheLabDeskSurface(modifier = Modifier.fillMaxWidth().heightIn(0.dp, 450.dp)) {
            DynamicIsland(viewModel = viewModel, islandUiState = IslandUiState.WelcomeState())
        }
        TheLabDeskSurface(modifier = Modifier.fillMaxWidth().heightIn(0.dp, 450.dp)) {
            DynamicIsland(
                viewModel = viewModel,
                modifier = Modifier.height(100.dp).clip(shape = RoundedCornerShape(35.dp)),
                islandUiState = IslandUiState.WelcomeState()
            )
        }
    }
}