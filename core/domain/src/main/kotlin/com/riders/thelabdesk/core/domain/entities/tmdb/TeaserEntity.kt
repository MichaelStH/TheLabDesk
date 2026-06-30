package com.riders.thelabdesk.core.domain.entities.tmdb

import com.riders.thelabdesk.core.common.utils.Constants

data class TeaserEntity(
    val id: Int,
    val name: String,
    val youtubeKey: String,
//    val url: String = "${Constants.VIDEO_YOUTUBE_WATCH_BASE_URL}$youtubeKey"
    val url: String = "${Constants.VIDEO_YOUTUBE_EMBED_BASE_URL}$youtubeKey"
) {
    companion object {
        fun getTMDBTeaserMock() = TeaserEntity(500, "Retribution", "THkf-fg5fdg3cdf")
    }
}
