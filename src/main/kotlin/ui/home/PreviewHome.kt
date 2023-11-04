package ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import core.compose.component.AutoSlidingCarousel
import core.compose.component.video.VideoPlayer
import core.compose.component.video.rememberVideoPlayerState
import core.compose.theme.TheLabDeskTheme
import core.compose.utils.AsyncBitmapImageFromNetwork
import core.compose.utils.AsyncSvgImage
import core.compose.utils.Text
import core.log.Timber
import kotlinx.coroutines.delay
import utils.Constants

//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselHeader(viewModel: HomeViewModel) {

    TheLabDeskTheme {
        Card(
            modifier = Modifier.fillMaxWidth().heightIn(0.dp, 350.dp).padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            AutoSlidingCarousel(
                modifier = Modifier.fillMaxSize(),
                itemsCount = viewModel.carouselList.size,
                itemContent = { index ->
                    BoxWithConstraints(
                        modifier = Modifier.fillMaxSize().padding(0.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        when (index) {
                            0 -> {
                                // Load home screen image welcoming
                                WelcomeContent()
                            }

                            1 -> {
                                AsyncSvgImage(
                                    url = viewModel.carouselList[index],
                                    density = LocalDensity.current
                                )
                            }

                            4 -> {
                                AsyncSvgImage(
                                    url = viewModel.carouselList[index],
                                    density = LocalDensity.current
                                )
                            }

                            else -> {
                                AsyncBitmapImageFromNetwork(
                                    modifier = Modifier.fillMaxWidth().height(this.maxHeight).padding(0.dp),
                                    url = viewModel.carouselList[index],
                                    contentScale = ContentScale.FillBounds
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun Home(viewModel: HomeViewModel) {
    val listState = rememberLazyListState()
    val videoState = rememberVideoPlayerState().also {
        it.toggleResume()
    }

    val showVideo = remember { mutableStateOf(false) }

    TheLabDeskTheme {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 12.dp),
            state = listState
        ) {
            item { CarouselHeader(viewModel) }

            item {
                val url = "${Constants.VIDEO_YOUTUBE_WATCH_BASE_URL}N1J-Hzqhvck"
                Timber.d("url: $url")

                AnimatedVisibility(visible = showVideo.value) {
                    BoxWithConstraints(
                        modifier = Modifier.width(880.dp).height(320.dp).background(Color.Black).wrapContentSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        VideoPlayer(
                            modifier = Modifier.fillMaxSize(),
                            url = url,
                            state = videoState,
                            onFinish = videoState::stopPlayback
                        )
                    }
                }
            }

            item {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        // viewModel.updateText("Hello, Desktop!")
                    }
                ) {
                    Text(text = "Hello, Desktop!")
                }
            }
        }
    }

    /*LaunchedEffect(showVideo) {
        delay(1_500)
        showVideo.value = true
    }*/
}

//////////////////////////////////////////////////
//
// PREVIEWS
//
//////////////////////////////////////////////////
@Preview
@Composable
private fun PreviewHome() {
    val viewModel = HomeViewModel()
    TheLabDeskTheme {
        Home(viewModel = viewModel)
    }
}
