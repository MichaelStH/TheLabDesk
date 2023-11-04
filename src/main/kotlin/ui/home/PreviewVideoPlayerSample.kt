package ui.home

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.compose.component.video.VideoPlayer
import core.compose.component.video.rememberVideoPlayerState
import utils.Constants


//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@Preview
@Composable
fun VideoPlayerSample() {
    val title = "Video Player"
    val desc = "Implementation of a video player.\n\nHere's a preview of the video player use for theater item previews"

    val videoState = rememberVideoPlayerState()

    HomeSectionContent(title = title, description = desc) {
        Card(modifier = Modifier.width(400.dp).height(200.dp), shape = RoundedCornerShape(16.dp)) {
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize().background(Color.Black).wrapContentSize().clip(shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                VideoPlayer(
                    modifier = Modifier.fillMaxSize(),
                    url = Constants.VIDEO_BUNNY_URL,
                    state = videoState,
                    onFinish = videoState::stopPlayback
                )
            }
        }
    }
}