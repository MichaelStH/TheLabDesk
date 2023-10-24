package data.remote

import data.remote.dto.NewsDto
import data.remote.dto.tmdb.TMDBResponse

interface IApi {
    suspend fun getNews(): List<NewsDto>

    suspend fun getMovies(): TMDBResponse
}