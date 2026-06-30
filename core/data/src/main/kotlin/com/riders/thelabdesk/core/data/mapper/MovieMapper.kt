package com.riders.thelabdesk.core.data.mapper

import com.riders.thelabdesk.core.data.local.model.tmdb.MovieModel
import com.riders.thelabdesk.core.data.remote.dto.tmdb.MovieDto
import com.riders.thelabdesk.core.domain.entities.tmdb.MovieEntity
import kotools.types.collection.toNotEmptySet
import kotools.types.number.toPositiveInt
import kotools.types.text.toNotBlankString


fun MovieDto.toModel(): MovieModel = MovieModel(this)
fun MovieDto.toEntity(): MovieEntity = MovieEntity(
    id = id.toPositiveInt().getOrThrow(),
    title = title.toNotBlankString().getOrThrow(),
    adult = adult,
    backdropPath = backdropPath.toNotBlankString().getOrElse { "N/A".toNotBlankString().getOrThrow() },
    genresID = genresID.toNotEmptySet().getOrThrow(),
    originalLanguage = originalLanguage.toNotBlankString().getOrThrow(),
    originalTitle = originalTitle.toNotBlankString().getOrThrow(),
    overview = overview.toNotBlankString().getOrElse { "N/A".toNotBlankString().getOrThrow() },
    popularity = popularity,
    poster = poster.toNotBlankString().getOrThrow(),
    releaseDate = releaseDate.toNotBlankString().getOrThrow(),
    video = video,
    rating = rating,
    voteNumber = voteNumber.toPositiveInt().getOrThrow()
)