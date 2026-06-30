package com.riders.thelabdesk.core.data.repository

import com.riders.thelabdesk.core.common.log.Timber
import com.riders.thelabdesk.core.data.mapper.toEntity
import com.riders.thelabdesk.core.data.remote.dto.news.NewsDto
import com.riders.thelabdesk.core.data.remote.dto.tmdb.TMDBMovieResponse
import com.riders.thelabdesk.core.data.remote.dto.tmdb.TMDBTvShowsResponse
import com.riders.thelabdesk.core.data.remote.dto.tmdb.TMDBVideoResponse
import com.riders.thelabdesk.core.data.utils.Constants
import com.riders.thelabdesk.core.domain.entities.news.NewsEntity
import com.riders.thelabdesk.core.domain.entities.tmdb.MovieEntity
import com.riders.thelabdesk.core.domain.entities.tmdb.TvShowsEntity
import com.riders.thelabdesk.core.domain.entities.tmdb.VideoEntity
import com.riders.thelabdesk.core.domain.repository.remote.IApi
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import java.io.IOException

internal class ApiImpl : IApi {

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
    override suspend fun getNews(): List<NewsEntity> {
        val newsJson =
            "useResource(resourcePath = \"json/news.json\") {json.encodeToString(String(it.readAllBytes())) }"

        Timber.e("newsJson: $newsJson")

        delay(500)

        val processedNewsJson = newsJson.substring(1, newsJson.length - 1).replace("\\", "")
        Timber.v("processedNewsJson: $processedNewsJson")

        delay(500)

        val news = json.decodeFromString<List<NewsDto>>(processedNewsJson)
        return news.mapIndexed { index, item -> NewsDto(index, item).toEntity() }
    }

    override suspend fun getTrendingMovies(): List<MovieEntity> {
        val url = "${Constants.BASE_URL_TMDB_ENDPOINT}/movie/now_playing?api_key=${Constants.TMDB_API_KEY}"
        Timber.d("getTrendingMovies() | url: $url")

        val response: TMDBMovieResponse = mClient.get(url).body<TMDBMovieResponse>()

        Timber.d("total count found: ${response.totalResults}")

        return response.results.map { it.toEntity() }
    }

    override suspend fun getPopularMovies(): List<MovieEntity> {
        val url = "${Constants.BASE_URL_TMDB_ENDPOINT}/movie/popular?api_key=${Constants.TMDB_API_KEY}"
        Timber.d("getPopularMovies() | url: $url")

        val response: TMDBMovieResponse = mClient.get(url).body<TMDBMovieResponse>()

        Timber.d("total count found: ${response.totalResults}")

        return response.results.map { it.toEntity() }
    }

    override suspend fun getUpcomingMovies(): List<MovieEntity> {
        val url = "${Constants.BASE_URL_TMDB_ENDPOINT}/movie/upcoming?api_key=${Constants.TMDB_API_KEY}"
        Timber.d("getUpcomingMovies() | url: $url")

        val response: TMDBMovieResponse = mClient.get(url).body<TMDBMovieResponse>()

        Timber.d("total count found: ${response.totalResults}")

        return response.results.map { it.toEntity() }
    }

    override suspend fun getTrendingTvShows(): List<TvShowsEntity> {
        val url = "${Constants.BASE_URL_TMDB_ENDPOINT}/tv/airing_today?api_key=${Constants.TMDB_API_KEY}"
        Timber.d("getTrendingTvShows() | url: $url")

        val response: TMDBTvShowsResponse = mClient.get(url).body<TMDBTvShowsResponse>()

        Timber.d("total count found: ${response.totalResults}")

        return response.results.map { it.toEntity() }
    }

    override suspend fun getPopularTvShows(): List<TvShowsEntity> {
        val url = "${Constants.BASE_URL_TMDB_ENDPOINT}/tv/popular?api_key=${Constants.TMDB_API_KEY}"
        Timber.d("getPopularTvShows() | url: $url")

        val response: TMDBTvShowsResponse = mClient.get(url).body<TMDBTvShowsResponse>()

        Timber.d("total count found: ${response.totalResults}")

        return response.results.map { it.toEntity() }
    }

    override suspend fun getMovies(): List<MovieEntity> {
        val url = "${Constants.BASE_URL_TMDB_ENDPOINT}/movie/now_playing?api_key=${Constants.TMDB_API_KEY}"
        Timber.d("getMovies() | url: $url")

        val response: TMDBMovieResponse = mClient.get(url).body<TMDBMovieResponse>()

        Timber.d("total count found: ${response.totalResults}")

        return response.results.map { it.toEntity() }
    }

    override suspend fun getMovieVideos(movieID: Int): List<VideoEntity>? {
        val url = "${Constants.BASE_URL_TMDB_ENDPOINT}/movie/$movieID/videos?api_key=${Constants.TMDB_API_KEY}"
        Timber.d("getMovieVideos() | url: $url")

        val response: TMDBVideoResponse = mClient.get(url).body<TMDBVideoResponse>()

        return run {
            Timber.d("total videos found: ${response.results.size}")
            response.results.map { it.toEntity() }
        }
    }

    override suspend fun getTvShowVideos(thShowID: Int): List<VideoEntity>? {
        val url = "${Constants.BASE_URL_TMDB_ENDPOINT}/tv/$thShowID/videos?api_key=${Constants.TMDB_API_KEY}"
        Timber.d("getTvShowVideos() | url: $url")

        val response: TMDBVideoResponse = mClient.get(url).body<TMDBVideoResponse>()

        return run {
            Timber.d("response: $response")
            Timber.d("total videos found: ${response.results}")
            response.results.map { it.toEntity() }
        }
    }
}