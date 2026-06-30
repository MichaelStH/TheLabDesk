package com.riders.thelabdesk.core.data.local.model.tmdb

import com.riders.thelabdesk.core.data.utils.Constants

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
