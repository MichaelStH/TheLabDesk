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
) : Serializable {

    var id: Int = 0

    constructor(
        id: Int,
        title: String,
        category: String,
        description: String,
        thumbnailUrl: String,
        date: String
    ) : this(title, category, description, thumbnailUrl, date) {
        this.id = id
    }

    constructor(
        id: Int,
        item: NewsDto
    ) : this(item.title, item.category, item.description, item.thumbnailUrl, item.date) {
        this.id = id
    }
}
