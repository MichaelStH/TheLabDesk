package data.remote.dto.tmdb

import kotlinx.serialization.SerialName
import java.io.Serializable

@kotlinx.serialization.Serializable
data class TMDBResponse(
    val dates:Dates,
    val page:Int,
    @SerialName(value = "results")
    val results:List<MovieDto>,
    @SerialName(value = "total_pages")
    val totalPages:Int,
    @SerialName(value = "total_results")
    val totalResults:Int
): Serializable {
}