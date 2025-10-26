package core.compose.component.video

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import core.log.Timber
import core.utils.VLCManager
import core.utils.emitProgressTo
import core.utils.getVideoSurfaceComponent
import core.utils.mediaPlayer
import core.utils.setupVideoFinishHandler
import data.local.model.compose.Progress
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.component.MediaPlayerComponent
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import uk.co.caprica.vlcj.player.list.MediaListPlayer
import uk.co.caprica.vlcj.player.list.MediaListPlayerEventAdapter
import utils.toPercentage
import java.awt.Component

@Composable
fun VideoPlayerImpl(
    url: String,
    isResumed: Boolean,
    volume: Float,
    speed: Float,
    seek: Float,
    isFullscreen: Boolean,
    progressState: MutableState<Progress>,
    modifier: Modifier,
    onFinish: (() -> Unit)?
) {
    var isSwingPanelLoaded: Boolean by remember { mutableStateOf(false) }

    val mediaPlayerEventListener = object : MediaPlayerEventAdapter() {
        override fun playing(mediaPlayer: MediaPlayer?) {
            super.playing(mediaPlayer)
            Timber.tag("VideoPlayerImpl").d("MediaPlayerEventAdapter.playing()")
        }
    }

    val mediaListPlayerEventListener = object : MediaListPlayerEventAdapter() {
        override fun mediaListPlayerFinished(mediaListPlayer: MediaListPlayer?) {
            super.mediaListPlayerFinished(mediaListPlayer)
            Timber
                .tag("VideoPlayerImpl")
                .d("mediaListPlayerFinished() | media list : ${mediaListPlayer?.list()?.media()?.count()}")
        }
    }


    val playerComponent: Component? = remember { VLCManager.mediaPlayerComponent }

    playerComponent?.let { component ->

        val videoSurface: Component = (component as MediaPlayerComponent).getVideoSurfaceComponent()
        videoSurface.isVisible = true

        val mediaPlayer: EmbeddedMediaPlayer = remember { component.mediaPlayer() }

        mediaPlayer.emitProgressTo(progressState)
        mediaPlayer.setupVideoFinishHandler(onFinish)

        LaunchedEffect(mediaPlayer) {
            try {
//                Timber.tag("VideoPlayerImpl").d("LaunchedEffect | mediaPlayer | add media list player events listener")
               //  mediaPlayer.subitems().events().addMediaListPlayerEventListener(mediaListPlayerEventListener)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }

        val factory = remember { { component } }

        /* OR the following code and using SwingPanel(factory = { factory }, ...) */

        // val factory by rememberUpdatedState(mediaPlayerComponent)

        /*OR .start*/
        LaunchedEffect(isSwingPanelLoaded) {
            if (!isSwingPanelLoaded) {
                Timber.tag("VideoPlayerImpl").e("LaunchedEffect | Swing panel not loaded yet")
                return@LaunchedEffect
            }

            Timber.tag("VideoPlayerImpl").i("LaunchedEffect | Attempting to play url: $url")
            mediaPlayer.media().play(url)
        }
        LaunchedEffect(seek) { mediaPlayer.controls().setPosition(seek) }
        LaunchedEffect(speed) { mediaPlayer.controls().setRate(speed) }
        LaunchedEffect(volume) { mediaPlayer.audio().setVolume(volume.toPercentage()) }
        LaunchedEffect(isResumed) { mediaPlayer.controls().setPause(!isResumed) }
        LaunchedEffect(isFullscreen) {
            if (mediaPlayer is EmbeddedMediaPlayer) {
                /*
                 * To be able to access window in the commented code below,
                 * extend the player composable function from WindowScope.
                 * See https://github.com/JetBrains/compose-jb/issues/176#issuecomment-812514936
                 * and its subsequent comments.
                 *
                 * We could also just fullscreen the whole window:
                 * `window.placement = WindowPlacement.Fullscreen`
                 * See https://github.com/JetBrains/compose-multiplatform/issues/1489
                 */

                // mediaPlayer.fullScreen().strategy(ExclusiveModeFullScreenStrategy(window))
                mediaPlayer.fullScreen().toggle()
            }
        }


        DisposableEffect(Unit) {
            onDispose {
                Thread.sleep(100)
                Timber.tag("VideoPlayerImpl").e("DisposableEffect | onDispose | releasing media player....")
                mediaPlayer.release()
            }
        }

        SwingPanel( // <--- Swing panel as root for hierarchy where drawing over heavyweight components needed (Swing/Compose switching trick START)
            modifier = Modifier.fillMaxSize().background(Color.Black).zIndex(0f),
            factory = {
                ComposePanel().apply { // <--- Switch back to Compose world for layout management purposes (Swing/Compose switching trick END)
                    setContent {
                        Box(
                            modifier = Modifier.fillMaxSize().background(Color.Black)
                        ) { // <--- Here we use Box to make layering of SwingPanels
                            SwingPanel(
                                factory = factory,
                                background = Color.Black,
                                modifier = modifier
                            ) { panel ->
                                Timber.tag("VideoPlayerImpl").d("Recomposition | Box.SwingPanel.update()")
                                isSwingPanelLoaded = true

                                panel.background = java.awt.Color.BLACK
                                panel.isVisible = true
                                panel.isFocusable = true

                                panel.requestFocusInWindow()
                            }
                        }
                    }
                }
            })
    } ?: run {
        Timber.tag("VideoPlayerImpl").e("Recomposition | Unable to initialize Media Player Component")
    }
}