package data.local.model.tmdb

import androidx.compose.runtime.Stable
import data.remote.dto.tmdb.MovieDto
import kotools.types.collection.NotEmptySet
import kotools.types.collection.notEmptySetOf
import kotools.types.collection.toNotEmptySet
import kotools.types.number.PositiveInt
import kotools.types.number.toPositiveInt
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString

@Stable
data class MovieModel(
    val id: PositiveInt,
    val title: NotBlankString,
    val adult: Boolean,
    val backdropPath: NotBlankString,
    val genresID: NotEmptySet<Int>,
    val originalLanguage: NotBlankString,
    val originalTitle: NotBlankString,
    val overview: NotBlankString,
    val popularity: Double,
    val poster: NotBlankString,
    val releaseDate: NotBlankString,
    val video: Boolean,
    val rating: Double,
    val voteNumber: PositiveInt,
) {

    constructor() : this(
        0.toPositiveInt().getOrThrow(),
        "".toNotBlankString().getOrThrow(),
        false,
        "".toNotBlankString().getOrThrow(),
        notEmptySetOf<Int>(0),
        "".toNotBlankString().getOrThrow(),
        "".toNotBlankString().getOrThrow(),
        "".toNotBlankString().getOrThrow(),
        0.0,
        "".toNotBlankString().getOrThrow(),
        "".toNotBlankString().getOrThrow(),
        false,
        0.0,
        0.toPositiveInt().getOrThrow()
    )

    constructor(movieDto: MovieDto) : this(
        movieDto.id.toPositiveInt().getOrThrow(),
        movieDto.title.toNotBlankString().getOrThrow(),
        movieDto.adult,
        movieDto.backdropPath.toNotBlankString().getOrThrow(),
        movieDto.genresID.toNotEmptySet().getOrThrow(),
        movieDto.originalLanguage.toNotBlankString().getOrThrow(),
        movieDto.originalTitle.toNotBlankString().getOrThrow(),
        movieDto.overview.toNotBlankString().getOrThrow(),
        movieDto.popularity,
        movieDto.poster.toNotBlankString().getOrThrow(),
        movieDto.releaseDate.toNotBlankString().getOrThrow(),
        movieDto.video,
        movieDto.rating,
        movieDto.voteNumber.toPositiveInt().getOrThrow()
    )
}
