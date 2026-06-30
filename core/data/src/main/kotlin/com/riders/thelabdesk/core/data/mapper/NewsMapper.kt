package com.riders.thelabdesk.core.data.mapper

import com.riders.thelabdesk.core.data.remote.dto.news.NewsDto
import com.riders.thelabdesk.core.domain.entities.news.NewsEntity

fun NewsDto.toEntity(): NewsEntity = NewsEntity(
    title = this.title,
    category = this.category,
    description = this.description,
    thumbnailUrl = this.thumbnailUrl,
    date = this.date
)
