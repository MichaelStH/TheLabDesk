package data.local.model.tmdb

import androidx.compose.runtime.Stable
import data.remote.dto.tmdb.TvShowsDto
import kotools.types.collection.NotEmptySet
import kotools.types.collection.toNotEmptySet
import kotools.types.number.PositiveInt
import kotools.types.number.toPositiveInt
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString

@Stable
data class TvShowsModel(
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
) {
    constructor(tvShowsDto: TvShowsDto) : this(
        tvShowsDto.id.toPositiveInt().getOrThrow(),
        tvShowsDto.name.toNotBlankString().getOrThrow(),
        tvShowsDto.backdropPath?.run { this.toNotBlankString().getOrThrow() } ?: "N/A".toNotBlankString().getOrThrow(),
        tvShowsDto.genresID.map { it.toPositiveInt().getOrThrow() }.toNotEmptySet().getOrThrow(),
        tvShowsDto.originalLanguage.toNotBlankString().getOrThrow(),
        tvShowsDto.originalName.toNotBlankString().getOrThrow(),
        tvShowsDto.overview.toNotBlankString().getOrElse { "N/A".toNotBlankString().getOrThrow() },
        tvShowsDto.popularity,
        tvShowsDto.poster.toNotBlankString().getOrThrow(),
        tvShowsDto.firstAirDate.toNotBlankString().getOrElse { "N/A".toNotBlankString().getOrThrow() },
        tvShowsDto.rating,
        tvShowsDto.voteNumber.toPositiveInt().getOrThrow()
    )
}
