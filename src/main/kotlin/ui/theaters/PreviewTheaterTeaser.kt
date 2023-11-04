package ui.theaters

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.compose.component.TheLabDeskCard
import core.compose.component.video.VideoPlayer
import core.compose.component.video.rememberVideoPlayerState
import core.compose.theme.TheLabDeskTheme
import core.log.Timber
import core.utils.DisplayManager
import di.AppModule
import utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TheaterTeaserContent(viewModel: TheatersViewModel) {

    val width = 760.dp
    val height = 692.dp

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val videoState = rememberVideoPlayerState()

    val url = "${Constants.VIDEO_YOUTUBE_WATCH_BASE_URL}${viewModel.theaterItemIdSelected.second}"
    Timber.d("url: $url")

    // Calculate screen height to set height for card
    val cardHeight: Dp = (DisplayManager.getScreenHeight() - (DisplayManager.getScreenHeight() / 2)).dp

    TheLabDeskTheme {
        TheLabDeskCard(modifier = Modifier.width(width).height(cardHeight), shape = RoundedCornerShape(35.dp)) {
            Surface(modifier = Modifier.fillMaxWidth().fillMaxHeight().background(Color.Black), color = Color.Black) {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxSize()
                        .hoverable(interactionSource)
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedContent(
                        modifier = Modifier.width(this.maxWidth).height(this.maxHeight),
                        targetState = viewModel.startTheaterItemTeaserVideo
                    ) { target ->
                        if (!target) {
                            Box(modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(modifier = Modifier.fillMaxSize().align(Alignment.Center))
                            }
                        } else {
                            BoxWithConstraints(
                                modifier = Modifier.fillMaxSize().padding(vertical = 56.dp).background(Color.Black)
                            ) {
                                VideoPlayer(
                                    modifier = Modifier.fillMaxSize(),
                                    url = url,
                                    state = videoState,
                                    onFinish = videoState::stopPlayback
                                )
                            }

                                AnimatedVisibility(visible = isHovered, enter = fadeIn(), exit = fadeOut()){
                                    // Controls
                                    Box(modifier= Modifier.fillMaxSize().background(Color.Transparent)){
                                        LinearProgressIndicator(modifier = Modifier.align(Alignment.Center), progress = videoState.progress.value.fraction)
                                    }
                            }
                        }
                    }

                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.TopEnd),
                        onClick = {
                            viewModel.updateTheaterItemIdSelected(-1 to "")
                            viewModel.updateShowTeaserVideo(false)
                        },
                        colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(alpha = .8f)),
                        shape = CircleShape
                    ) {
                        Icon(
                            modifier = Modifier.size(30.dp).clip(shape = CircleShape),
                            imageVector = Icons.Rounded.Close,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewTheaterTeaserContent() {
    val theatersViewModel = TheatersViewModel(AppModule.injectDependencies())
    TheLabDeskTheme {
        TheaterTeaserContent(theatersViewModel)
    }
}