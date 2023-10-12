package data.remote.dto

import kotlinx.serialization.SerialName
import java.io.Serializable

@kotlinx.serialization.Serializable
data class NewsDto(
    @SerialName("title")
    val title: String,
    @SerialName("category")
    val category: String,
    @SerialName("description")
    val description: String,
    @SerialName("thumbnailUrl")
    val thumbnailUrl: String,
    @SerialName("date")
    val date: String
) : Serializable
