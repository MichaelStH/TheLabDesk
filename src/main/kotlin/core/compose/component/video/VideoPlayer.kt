package core.compose.component.video

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoPhotography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import core.compose.component.TheLabDeskText
import core.compose.theme.isSystemInDarkTheme
import data.local.model.compose.Progress
import data.local.model.compose.VideoPlayerState

////////////////////////////////////////////////////////
//
// COMPOSE
//
////////////////////////////////////////////////////////
@Composable
fun rememberVideoPlayerState(
    seek: Float = 0f,
    speed: Float = 1f,
    volume: Float = 1f,
    isResumed: Boolean = true,
    isFullscreen: Boolean = false
): VideoPlayerState = rememberSaveable(saver = VideoPlayerState.Saver()) {
    VideoPlayerState(
        seek,
        speed,
        volume,
        isResumed,
        isFullscreen,
        Progress(0f, 0)
    )
}

@Composable
fun VideoPlayer(
    url: String,
    state: VideoPlayerState,
    modifier: Modifier = Modifier,
    onFinish: (() -> Unit)? = null
) = VideoPlayerImpl(
    url = url,
    isResumed = state.isResumed,
    volume = state.volume,
    speed = state.speed,
    seek = state.seek,
    isFullscreen = state.isFullscreen,
    progressState = state._progress,
    modifier = modifier,
    onFinish = onFinish
)


////////////////////////////////////////////////////////
//
// PREVIEWS
//
////////////////////////////////////////////////////////
@Preview
@Composable
fun NoVideoPlayerFound() {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Image(
            modifier = Modifier.size(64.dp),
            imageVector = Icons.Default.NoPhotography,
            contentDescription = null,
            colorFilter = ColorFilter.tint(if (!isSystemInDarkTheme()) Color.Black else Color.White)
        )

        TheLabDeskText(
            modifier = Modifier.fillMaxWidth(),
            text = "No Video Player found. Please install Vlc for this feature to work",
            style = TextStyle(textAlign = TextAlign.Center)
        )
    }
}