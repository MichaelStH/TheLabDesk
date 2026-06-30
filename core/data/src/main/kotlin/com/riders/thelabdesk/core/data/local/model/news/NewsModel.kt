package com.riders.thelabdesk.core.data.local.model.news

import kotlinx.serialization.SerialName
import java.io.Serializable

data class NewsModel(
    val title: String,
    val category: String,
    val description: String,
    val thumbnailUrl: String,
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
        item: NewsModel
    ) : this(item.title, item.category, item.description, item.thumbnailUrl, item.date) {
        this.id = id
    }
}