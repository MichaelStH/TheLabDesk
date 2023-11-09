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
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import core.compose.component.TheLabDeskCard
import core.compose.component.video.VideoPlayer
import core.compose.component.video.rememberVideoPlayerState
import core.compose.theme.TheLabDeskTheme
import core.log.Timber
import core.utils.DisplayManager
import data.local.model.tmdb.TDMBTeaserModel.Companion.getTMDBTeaserMock
import di.AppModule

private val width = 760.dp
private val height = 692.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TheaterTeaserContent(viewModel: TheatersViewModel) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val videoState = rememberVideoPlayerState()

    // val url = "${Constants.VIDEO_YOUTUBE_WATCH_BASE_URL}${viewModel.theaterItemIdSelected.second}"
    Timber.d("url: ${viewModel.tmdbTeaser?.url}")

    // Calculate screen height to set height for card
//    val cardHeight: Dp = 400.dp
    val cardHeight: Dp = (DisplayManager.getScreenHeight() - (DisplayManager.getScreenHeight() / 2)).dp

    TheLabDeskTheme {
        TheLabDeskCard(
            modifier = Modifier.width(width).height(cardHeight).hoverable(interactionSource),
            shape = RoundedCornerShape(35.dp)
        ) {
            Surface(modifier = Modifier.fillMaxWidth().fillMaxHeight().background(Color.Black), color = Color.Black) {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedContent(
                        modifier = Modifier.width(this.maxWidth).height(this.maxHeight),
                        targetState = null != viewModel.tmdbTeaser
                    ) { target ->
                        if (!target) {
                            Box(
                                modifier = Modifier.size(30.dp).align(Alignment.Center).zIndex(10f),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp).align(Alignment.Center))
                            }
                        } else {
                            BoxWithConstraints(
                                modifier = Modifier.fillMaxSize().padding(vertical = 56.dp).background(Color.Black)
                                    .zIndex(0f)
                            ) {
                                VideoPlayer(
                                    modifier = Modifier.fillMaxSize(),
                                    url = viewModel.tmdbTeaser?.url!!,
                                    state = videoState,
                                    onFinish = videoState::stopPlayback
                                )
                            }
                        }
                    }

                    AnimatedVisibility(visible = isHovered, enter = fadeIn(), exit = fadeOut()) {
                        // Controls
                        Box(modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
                            Text(
                                modifier = Modifier.fillMaxWidth(.95f).align(Alignment.TopStart)
                                    .padding(top = 20.dp, start = 30.dp),
                                text = "${viewModel.tmdbTeaser?.name} Teaser",
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth().padding(10.dp).align(Alignment.BottomCenter),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                IconButton(onClick = { videoState.toggleResume() }) {
                                    Icon(
                                        imageVector = if (!videoState.isResumed) Icons.Filled.PlayArrow else Icons.Filled.Pause,
                                        contentDescription = null
                                    )
                                }

                                LinearProgressIndicator(
                                    modifier = Modifier.fillMaxWidth().padding(end = 16.dp),
                                    progress = videoState.progress.value.fraction
                                )
                            }
                        }
                    }

                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.TopEnd),
                        onClick = {
                            viewModel.updateTmdbTeaser(null)
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

                    // Loading Prgoress Bar
                    AnimatedVisibility(
                        modifier = Modifier.align(Alignment.Center),
                        visible = null == viewModel.tmdbTeaser
                    ) {
                        Box(
                            modifier = Modifier.size(30.dp).align(Alignment.Center),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp).align(Alignment.Center))
                        }
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
    TheLabDeskTheme { TheaterTeaserContent(theatersViewModel) }
}