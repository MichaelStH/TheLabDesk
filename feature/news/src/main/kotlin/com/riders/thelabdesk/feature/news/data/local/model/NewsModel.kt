package com.riders.thelabdesk.feature.news.data.local.model

import androidx.compose.runtime.Stable

@Stable
data class NewsModel(
    val id: Int,
    val title: String,
    val category: String,
    val description: String,
    val thumbnailUrl: String,
    val date: String
)

fun com.riders.thelabdesk.core.domain.entities.news.NewsEntity.toUiModel() = NewsModel(
    id = this.id,
    title = this.title,
    category = this.category,
    description = this.description,
    thumbnailUrl = this.thumbnailUrl,
    date = this.date
)
