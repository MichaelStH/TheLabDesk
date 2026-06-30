package com.riders.thelabdesk.core.domain.repository.remote

import com.riders.thelabdesk.core.domain.entities.news.NewsEntity
import com.riders.thelabdesk.core.domain.entities.tmdb.MovieEntity
import com.riders.thelabdesk.core.domain.entities.tmdb.TvShowsEntity
import com.riders.thelabdesk.core.domain.entities.tmdb.VideoEntity

interface IApi {

    suspend fun getNews(): List<NewsEntity>

    suspend fun getTrendingMovies(): List<MovieEntity>
    suspend fun getPopularMovies(): List<MovieEntity>
    suspend fun getUpcomingMovies(): List<MovieEntity>
    suspend fun getTrendingTvShows(): List<TvShowsEntity>
    suspend fun getPopularTvShows(): List<TvShowsEntity>
    suspend fun getMovies(): List<MovieEntity>
    suspend fun getMovieVideos(movieID: Int): List<VideoEntity>?
    suspend fun getTvShowVideos(thShowID: Int): List<VideoEntity>?
}