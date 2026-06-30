package com.riders.thelabdesk.core.data.mapper

import com.riders.thelabdesk.core.data.remote.dto.tmdb.TvShowsDto
import com.riders.thelabdesk.core.domain.entities.tmdb.TvShowsEntity
import kotools.types.collection.toNotEmptySet
import kotools.types.number.toPositiveInt
import kotools.types.text.toNotBlankString


fun TvShowsDto.toEntity(): TvShowsEntity = TvShowsEntity(
    id = id.toPositiveInt().getOrThrow(),
    title = name.toNotBlankString().getOrThrow(),
    backdropPath = backdropPath?.run { this.toNotBlankString().getOrThrow() } ?: "N/A".toNotBlankString().getOrThrow(),
    genresID = genresID.map { it.toPositiveInt().getOrThrow() }.toNotEmptySet().getOrThrow(),
    originalLanguage = originalLanguage.toNotBlankString().getOrThrow(),
    originalTitle = originalName.toNotBlankString().getOrThrow(),
    overview = overview.toNotBlankString().getOrElse { "N/A".toNotBlankString().getOrThrow() },
    popularity = popularity,
    poster = poster.toNotBlankString().getOrThrow(),
    firstAirDate = firstAirDate.toNotBlankString().getOrElse { "N/A".toNotBlankString().getOrThrow() },
    rating = rating,
    voteNumber = voteNumber.toPositiveInt().getOrThrow()
)