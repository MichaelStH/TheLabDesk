package ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.compose.component.video.VideoPlayer
import core.compose.component.video.rememberVideoPlayerState
import core.compose.theme.TheLabDeskTheme
import core.compose.utils.AsyncBitmapImageFromNetwork
import core.compose.utils.Text
import core.log.Timber
import kotlinx.coroutines.delay
import ui.WelcomeContent
import utils.Constants

//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@Composable
fun Home(viewModel: HomeViewModel) {
    val scope = rememberCoroutineScope()
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

            item {
                WelcomeContent()
            }

            item {
                BoxWithConstraints(modifier = Modifier.fillMaxWidth(), Alignment.Center) {
                    Card(
                        modifier = Modifier.width(this.maxWidth / 1.5f).heightIn(0.dp, 300.dp).padding(20.dp),
                        shape = RoundedCornerShape(35.dp)
                    ) {
                        AsyncBitmapImageFromNetwork(
                            modifier = Modifier.fillMaxWidth(),
                            url = Constants.IMAGE_MARVEL_LOGO_URL
                        )
                    }
                }
            }
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

    LaunchedEffect(showVideo) {
        delay(1_500)
        showVideo.value = true
    }
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
