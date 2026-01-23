package com.riders.thelabdesk.core.video.data.local.compose

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import com.riders.thelabdesk.core.video.data.local.compose.VideoPlayerState.Companion.Saver

@Stable
class VideoPlayerState(
    seek: Float = 0f,
    speed: Float = 1f,
    volume: Float = 1f,
    isResumed: Boolean = true,
    isFullscreen: Boolean = false,
    progress: VideoProgress
) {
    var seek by mutableStateOf(seek)
    var speed by mutableStateOf(speed)
    var volume by mutableStateOf(volume)
    var isResumed by mutableStateOf(isResumed)
    var isFullscreen by mutableStateOf(isFullscreen)
    internal val _progress = mutableStateOf(progress)
    val progress: State<VideoProgress> = _progress

    fun toggleResume() {
        isResumed = !isResumed
    }

    fun toggleFullscreen() {
        isFullscreen = !isFullscreen
    }

    fun stopPlayback() {
        isResumed = false
    }

    companion object {
        /**
         * The default [Saver] implementation for [VideoPlayerState].
         */
        fun Saver() = listSaver<VideoPlayerState, Any>(
            save = {
                listOf(
                    it.seek,
                    it.speed,
                    it.volume,
                    it.isResumed,
                    it.isFullscreen,
                    it.progress.value
                )
            },
            restore = {
                VideoPlayerState(
                    seek = it[0] as Float,
                    speed = it[1] as Float,
                    volume = it[2] as Float,
                    isResumed = it[3] as Boolean,
                    isFullscreen = it[4] as Boolean,
                    progress = it[5] as VideoProgress,
                )
            }
        )
    }
}