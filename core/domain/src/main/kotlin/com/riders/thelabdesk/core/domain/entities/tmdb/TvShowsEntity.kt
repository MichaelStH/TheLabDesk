package com.riders.thelabdesk.core.domain.entities.tmdb

import kotools.types.collection.NotEmptySet
import kotools.types.number.PositiveInt
import kotools.types.text.NotBlankString

data class TvShowsEntity(
    val id: PositiveInt,
    val title: NotBlankString,
    val backdropPath: NotBlankString,
    val genresID: NotEmptySet<PositiveInt>,
    val originalLanguage: NotBlankString,
    val originalTitle: NotBlankString,
    val overview: NotBlankString,
    val popularity: Double,
    val poster: NotBlankString,
    val firstAirDate: NotBlankString,
    val rating: Double,
    val voteNumber: PositiveInt,
)
