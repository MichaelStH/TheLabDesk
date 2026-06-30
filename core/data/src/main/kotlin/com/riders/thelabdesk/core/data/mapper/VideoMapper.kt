package com.riders.thelabdesk.core.data.mapper

import com.riders.thelabdesk.core.data.remote.dto.tmdb.VideoDto
import com.riders.thelabdesk.core.domain.entities.tmdb.VideoEntity

fun VideoDto.toEntity(): VideoEntity = VideoEntity(
    id = id,
    iso_639_1 = iso_639_1,
    iso_3166_1 = iso_3166_1,
    name = name,
    key = key,
    site = site,
    size = size,
    type = type,
    isOfficial = official,
    publishedAt = publishedAt
)