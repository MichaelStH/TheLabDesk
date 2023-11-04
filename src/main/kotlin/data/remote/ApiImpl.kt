package data.remote

import androidx.compose.ui.res.useResource
import core.log.Timber
import data.remote.dto.NewsDto
import data.remote.dto.tmdb.TMDBMovieResponse
import data.remote.dto.tmdb.TMDBTvShowsResponse
import data.remote.dto.tmdb.TMDBVideoResponse
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
            json(
                Json {
                isLenient = true
                ignoreUnknownKeys = true
                coerceInputValues = true
            }) // Example: Register JSON content transformation
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
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
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

    override suspend fun getTrendingMovies(): TMDBMovieResponse {
        val url = "${Constants.BASE_URL_TMDB_ENDPOINT}/movie/now_playing?api_key=${Constants.TMDB_API_KEY}"
        Timber.d("getTrendingMovies() | url: $url")

        val response: TMDBMovieResponse = mClient.get(url).body<TMDBMovieResponse>()

        if (null == response) {
            Timber.e("response is null")
        } else {
            Timber.d("total count found: ${response.totalResults}")
        }

        return response
    }

    override suspend fun getPopularMovies(): TMDBMovieResponse {
        val url = "${Constants.BASE_URL_TMDB_ENDPOINT}/movie/popular?api_key=${Constants.TMDB_API_KEY}"
        Timber.d("getPopularMovies() | url: $url")

        val response: TMDBMovieResponse = mClient.get(url).body<TMDBMovieResponse>()

        if (null == response) {
            Timber.e("response is null")
        } else {
            Timber.d("total count found: ${response.totalResults}")
        }

        return response
    }

    override suspend fun getUpcomingMovies(): TMDBMovieResponse {
        val url = "${Constants.BASE_URL_TMDB_ENDPOINT}/movie/upcoming?api_key=${Constants.TMDB_API_KEY}"
        Timber.d("getUpcomingMovies() | url: $url")

        val response: TMDBMovieResponse = mClient.get(url).body<TMDBMovieResponse>()

        if (null == response) {
            Timber.e("response is null")
        } else {
            Timber.d("total count found: ${response.totalResults}")
        }

        return response
    }

    override suspend fun getTrendingTvShows(): TMDBTvShowsResponse {
        val url = "${Constants.BASE_URL_TMDB_ENDPOINT}/tv/airing_today?api_key=${Constants.TMDB_API_KEY}"
        Timber.d("getTrendingTvShows() | url: $url")

        val response: TMDBTvShowsResponse = mClient.get(url).body<TMDBTvShowsResponse>()

        if (null == response) {
            Timber.e("response is null")
        } else {
            Timber.d("total count found: ${response.totalResults}")
        }

        return response
    }

    override suspend fun getPopularTvShows(): TMDBTvShowsResponse {
        val url = "${Constants.BASE_URL_TMDB_ENDPOINT}/tv/popular?api_key=${Constants.TMDB_API_KEY}"
        Timber.d("getPopularTvShows() | url: $url")

        val response: TMDBTvShowsResponse = mClient.get(url).body<TMDBTvShowsResponse>()

        if (null == response) {
            Timber.e("response is null")
        } else {
            Timber.d("total count found: ${response.totalResults}")
        }

        return response
    }

    override suspend fun getMovies(): TMDBMovieResponse {
        val url = "${Constants.BASE_URL_TMDB_ENDPOINT}/movie/now_playing?api_key=${Constants.TMDB_API_KEY}"
        Timber.d("getMovies() | url: $url")

        val response: TMDBMovieResponse = mClient.get(url).body<TMDBMovieResponse>()

        if (null == response) {
            Timber.e("response is null")
        } else {
            Timber.d("total count found: ${response.totalResults}")
        }

        return response
    }

    override suspend fun getVideos(movieID: Int): TMDBVideoResponse {
        val url = "${Constants.BASE_URL_TMDB_ENDPOINT}/movie/$movieID/videos?api_key=${Constants.TMDB_API_KEY}"
        Timber.d("getVideos() | url: $url")

        val response: TMDBVideoResponse = mClient.get(url).body<TMDBVideoResponse>()

        if (null == response) {
            Timber.e("response is null")
        } else {
            Timber.d("total videos found: ${response.results.size}")
        }

        return response
    }
}