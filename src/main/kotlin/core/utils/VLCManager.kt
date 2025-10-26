package core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import com.riders.thelabdesk.TheLabDeskApp
import com.sun.jna.Native
import com.sun.jna.NativeLibrary
import core.log.Timber
import data.local.model.compose.Progress
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import uk.co.caprica.vlcj.binding.lib.LibC
import uk.co.caprica.vlcj.binding.lib.LibVlc
import uk.co.caprica.vlcj.binding.support.runtime.RuntimeUtil
import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.component.CallbackMediaPlayerComponent
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent
import uk.co.caprica.vlcj.player.component.MediaPlayerComponent
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import java.awt.Component

/**
 * Manages VLC player initialization and component creation.
 * This object handles the discovery of the VLC library on the host system
 * and provides the appropriate media player component for UI integration.
 */
object VLCManager {

    /**
     * Lazily initializes and provides a media player [Component].
     *
     * The component is created by [initializeMediaPlayerComponent] only when first accessed.
     * It will be `null` if the VLC library is not found.
     */
    val mediaPlayerComponent: Component? by lazy { initializeMediaPlayerComponent() }

    /**
     * Checks if the VLC native library is available on the current system.
     *
     * For macOS, it manually sets environment variables and search paths for the default
     * VLC installation location (`/Applications/VLC.app`).
     * For other operating systems, it uses [NativeDiscovery] to find the library.
     *
     * @return `true` if the VLC library is found, `false` otherwise.
     */
    fun hasVLCLibrary() = if (SystemManager.isMacOs()) {
        // Check if VLC library can be found on current system
        runCatching {
            LibC.INSTANCE.setenv(
                "VLC_PLUGIN_PATH",
                "/Applications/VLC.app/Contents/MacOS/plugins",
                1
            )

            NativeLibrary.addSearchPath(
                RuntimeUtil.getLibVlcLibraryName(),
                "/Applications/VLC.app/Contents/MacOS/lib"
            )
            NativeLibrary.addSearchPath(
                RuntimeUtil.getLibVlcCoreLibraryName(),
                "/Applications/VLC.app/Contents/MacOS/lib"
            )

            Native.load(RuntimeUtil.getLibVlcCoreLibraryName(), LibC::class.java)
            Native.load(RuntimeUtil.getLibVlcLibraryName(), LibC::class.java)
            true
        }
            .onFailure {
                it.printStackTrace()
                Timber.e("initializeMediaPlayerComponent() | onFailure | Error caught with message : ${it.message} (class : ${it.javaClass.canonicalName})")
            }
            .onSuccess {
                Timber.d("initializeMediaPlayerComponent() | onSuccess")
            }
            .getOrElse { false }
    } else {
        // Check if VLC library can be found on current system
        if (!NativeDiscovery().discover()) {
            false
        } else {
            Timber.d("LibVlc found with version: ${LibVlc.libvlc_get_version()}")
            true
        }
    }


    /**
     * Initializes the appropriate media player component based on the operating system.
     *
     * If the VLC library is not found via [hasVLCLibrary], this function will return `null`
     * and update the application state accordingly.
     *
     * For macOS, it uses [CallbackMediaPlayerComponent] due to issues with the
     * heavyweight `EmbeddedMediaPlayerComponent`.
     * See https://github.com/caprica/vlcj/issues/887#issuecomment-503288294
     * for more context. For other systems, it uses [EmbeddedMediaPlayerComponent].
     *
     * @return A media player [Component] if VLC is found, otherwise `null`.
     */
    fun initializeMediaPlayerComponent(): Component? {
        Timber.d("initializeMediaPlayerComponent()")

        return if (!hasVLCLibrary()) {
            Timber.e("Unable to find VLC library file. Please make sure that VLC is installed on your system")
            TheLabDeskApp.updateVlcFoundLibrary(false)
            null
        } else {
            TheLabDeskApp.updateVlcFoundLibrary(true)

            val mediaPlayerComponent: Component = if (SystemManager.isMacOs()) {
                Timber.e("isMacOs() | Call CallbackMediaPlayerComponent()")
                CallbackMediaPlayerComponent()
            } else {
                Timber.e("NOT isMacOs() | Call EmbeddedMediaPlayerComponent()")
                EmbeddedMediaPlayerComponent()
            }

            return mediaPlayerComponent
        }
    }
}


////////////////////////////////////////////////////////////
//
// EXTENSIONS
//
////////////////////////////////////////////////////////////
/**
 * A Composable effect that sets up a listener to handle the video finishing.
 *
 * It plays the video again from the start when it stops, creating a loop.
 * This is used as a workaround because `mediaPlayer.controls().repeat = true` did not work as expected.
 *
 * @param onFinish An optional callback to be invoked when the media has finished playing,
 * before it restarts.
 */
@Composable
fun MediaPlayer.setupVideoFinishHandler(onFinish: (() -> Unit)?) {
    DisposableEffect(onFinish) {
        val listener = object : MediaPlayerEventAdapter() {
            override fun playing(mediaPlayer: MediaPlayer?) {
                Timber.d("MediaPlayerEventAdapter.playing()")
            }

            override fun stopped(mediaPlayer: MediaPlayer) {
                Timber.e("MediaPlayerEventAdapter.stopped()")
                onFinish?.invoke()
                mediaPlayer.controls().play()
            }
        }

        try {
            events().addMediaPlayerEventListener(listener)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        onDispose { events().removeMediaPlayerEventListener(listener) }
    }
}

/**
 * A Composable effect that periodically emits the media player's progress to a [MutableState].
 *
 * The progress is checked every 50 milliseconds. Note that vlcj itself might update
 * the progress less frequently (e.g., every ~250ms).
 *
 * @param state The [MutableState] of [Progress] to update with the current playback
 * position and time.
 */
@Composable
fun MediaPlayer.emitProgressTo(state: MutableState<Progress>) {
    LaunchedEffect(key1 = Unit) {
        while (isActive) {
            try {
                val fraction = status().position()
                val time = status().time()
                state.value = Progress(fraction.toFloat(), time)
                delay(50)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }
}

/**
 * Extension function to get the video surface component from a [MediaPlayerComponent].
 *
 * @return The underlying video surface [Component].
 * @throws IllegalStateException if called on a type that is not a known vlcj player component.
 */
fun MediaPlayerComponent.getVideoSurfaceComponent(): Component = when (this) {
    is CallbackMediaPlayerComponent -> videoSurfaceComponent()
    is EmbeddedMediaPlayerComponent -> videoSurfaceComponent()
    else -> error("videoSurfaceComponent() can only be called on vlcj player components")
}

/**
 * Extension function to get the [EmbeddedMediaPlayer] from a player [Component].
 *
 * This is a convenience function because [CallbackMediaPlayerComponent] and
 * [EmbeddedMediaPlayerComponent] do not share a common interface for the `mediaPlayer()` method.
 *
 * @return The underlying [EmbeddedMediaPlayer] instance.
 * @throws IllegalStateException if called on a type that is not a known vlcj player component.
 */
fun Component.mediaPlayer(): EmbeddedMediaPlayer = when (this) {
    is CallbackMediaPlayerComponent -> mediaPlayer()
    is EmbeddedMediaPlayerComponent -> mediaPlayer()
    else -> error("mediaPlayer() can only be called on vlcj player components")
}.apply {
    try {
        if (true == this is CallbackMediaPlayerComponent) {
            input().enableKeyInputHandling(false)
            input().enableMouseInputHandling(false)
        }
    } catch (exception: Exception) {
        exception.printStackTrace()
    }
}
