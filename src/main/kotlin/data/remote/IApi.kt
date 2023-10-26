package data.remote

import data.remote.dto.NewsDto
import data.remote.dto.tmdb.TMDBMovieResponse
import data.remote.dto.tmdb.TMDBTvShowsResponse

interface IApi {
    suspend fun getNews(): List<NewsDto>

    suspend fun getTrendingMovies(): TMDBMovieResponse
    suspend fun getPopularMovies(): TMDBMovieResponse
    suspend fun getUpcomingMovies(): TMDBMovieResponse
    suspend fun getTrendingTvShows(): TMDBTvShowsResponse
    suspend fun getPopularTvShows(): TMDBTvShowsResponse
    suspend fun getMovies(): TMDBMovieResponse
}