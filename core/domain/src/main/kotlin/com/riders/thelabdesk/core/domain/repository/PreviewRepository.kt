package com.riders.thelabdesk.core.domain.repository

import com.riders.thelabdesk.core.domain.entities.news.NewsEntity
import com.riders.thelabdesk.core.domain.entities.tmdb.MovieEntity
import com.riders.thelabdesk.core.domain.entities.tmdb.TvShowsEntity
import com.riders.thelabdesk.core.domain.entities.tmdb.VideoEntity

object PreviewRepository : IRepository {
    override suspend fun getNews(): List<NewsEntity> = emptyList()
    override suspend fun getTrendingMovies(): List<MovieEntity> = emptyList()
    override suspend fun getPopularMovies(): List<MovieEntity> = emptyList()
    override suspend fun getUpcomingMovies(): List<MovieEntity> = emptyList()
    override suspend fun getTrendingTvShows(): List<TvShowsEntity> = emptyList()
    override suspend fun getPopularTvShows(): List<TvShowsEntity> = emptyList()
    override suspend fun getMovies(): List<MovieEntity> = emptyList()
    override suspend fun getMovieVideos(movieID: Int): List<VideoEntity> = emptyList()
    override suspend fun getTvShowVideos(thShowID: Int): List<VideoEntity> = emptyList()
}
