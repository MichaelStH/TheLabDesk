package data.local.model.tmdb

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import utils.Constants

@Stable
@Immutable
data class TDMBTeaserModel(
    val id: Int,
    val name: String,
    val youtubeKey: String,
//    val url: String = "${Constants.VIDEO_YOUTUBE_WATCH_BASE_URL}$youtubeKey"
    val url: String = "${Constants.VIDEO_YOUTUBE_EMBED_BASE_URL}$youtubeKey"
) {
    companion object {
        fun getTMDBTeaserMock() = TDMBTeaserModel(500, "Retribution", "THkf-fg5fdg3cdf")
    }
}
