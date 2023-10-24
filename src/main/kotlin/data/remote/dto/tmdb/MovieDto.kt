package data.remote.dto.tmdb

import kotlinx.serialization.SerialName
import java.io.Serializable

@kotlinx.serialization.Serializable
data class MovieDto(
    @SerialName(value = "id")
    val id: Int,
    @SerialName(value = "title")
    val title: String,
    @SerialName(value = "adult")
    val adult: Boolean,
    @SerialName(value = "backdrop_path")
    val backdropPath: String,
    @SerialName(value = "genre_ids")
    val genresID: Set<Int>,
    @SerialName(value = "original_language")
    val originalLanguage: String,
    @SerialName(value = "original_title")
    val originalTitle: String,
    @SerialName(value = "overview")
    val overview: String,
    @SerialName(value = "popularity")
    val popularity: Double,
    @SerialName(value = "poster_path")
    val poster: String,
    @SerialName(value = "release_date")
    val releaseDate: String,
    @SerialName(value = "video")
    val video: Boolean,
    @SerialName(value = "vote_average")
    val rating: Double,
    @SerialName(value = "vote_count")
    val voteNumber: Int,
) : Serializable
