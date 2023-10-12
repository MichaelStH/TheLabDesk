package data.remote

import androidx.compose.ui.res.useResource
import core.log.Timber
import data.remote.dto.NewsDto
import kotlinx.coroutines.delay
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException

class ApiImpl : IApi {

    private val json = Json {
        // isLenient = true
        ignoreUnknownKeys = true
    }

    @Throws(IOException::class)
    override suspend fun getNews(): List<NewsDto> {
        val newsJson = useResource(resourcePath = "json/news.json") {
            json.encodeToString(String(it.readAllBytes()))
        }

        Timber.e("newsJson: $newsJson")

        delay(500)

        val processedNewsJson = newsJson.substring(1, newsJson.length - 1).replace("\\", "")
        Timber.v("processedNewsJson: $processedNewsJson")

        delay(500)

        val news = json.decodeFromString<List<NewsDto>>(processedNewsJson)
        return news.mapIndexed { index, item -> NewsDto(index, item) }
    }
}