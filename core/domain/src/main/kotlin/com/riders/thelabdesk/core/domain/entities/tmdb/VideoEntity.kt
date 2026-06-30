package com.riders.thelabdesk.core.domain.entities.tmdb

data class VideoEntity(
    val id: String,
    val iso_639_1: String,
    val iso_3166_1: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Int,
    val type: String,
    val isOfficial: Boolean,
    val publishedAt: String
)
