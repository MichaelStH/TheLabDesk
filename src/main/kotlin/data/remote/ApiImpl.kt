package data.remote

import androidx.compose.ui.res.useResource
import core.log.Timber
import data.remote.dto.NewsDto
import data.remote.dto.tmdb.TMDBResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.delay
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import utils.Constants
import java.io.IOException

class ApiImpl : IApi {
    private var mClient: HttpClient = HttpClient(CIO) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.BODY
            filter { request ->
                // request.url.host.contains("ktor.io")
                true
            }
        }

        install(ContentNegotiation) {
            json() // Example: Register JSON content transformation
            // Add more transformations as needed for other content types
        }

        defaultRequest {
            url {
//                    host = ("https://ktor.io/docs/")
                host = (Constants.BASE_URL_TMDB_ENDPOINT)
//                    path("/")
                parameters.append("api_key", Constants.TMDB_API_KEY)
            }
        }
    }

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

    override suspend fun getMovies(): TMDBResponse {
        val url = "${Constants.BASE_URL_TMDB_ENDPOINT}/movie/now_playing?api_key=${Constants.TMDB_API_KEY}"
        Timber.d("getMovies() | url: $url")

        val response: TMDBResponse = mClient.get(url).body<TMDBResponse>()

        if (null == response) {
            Timber.e("response is null")
        } else {
            Timber.d("total count found: ${response.totalResults}")
        }

        return response
    }
}