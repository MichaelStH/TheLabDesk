package com.riders.thelabdesk.core.data.repository

import com.riders.thelabdesk.core.domain.entities.news.NewsEntity
import com.riders.thelabdesk.core.domain.entities.tmdb.MovieEntity
import com.riders.thelabdesk.core.domain.entities.tmdb.TvShowsEntity
import com.riders.thelabdesk.core.domain.entities.tmdb.VideoEntity
import com.riders.thelabdesk.core.domain.repository.IRepository
import com.riders.thelabdesk.core.domain.repository.remote.IApi

internal class RepositoryImpl(api: IApi) : IRepository {
    private val mApi: IApi = api


    override suspend fun getNews(): List<NewsEntity> = mApi.getNews()


    override suspend fun getTrendingMovies(): List<MovieEntity> = mApi.getTrendingMovies()

    override suspend fun getPopularMovies(): List<MovieEntity> = mApi.getPopularMovies()

    override suspend fun getUpcomingMovies(): List<MovieEntity> = mApi.getUpcomingMovies()

    override suspend fun getTrendingTvShows(): List<TvShowsEntity> = mApi.getTrendingTvShows()

    override suspend fun getPopularTvShows(): List<TvShowsEntity> = mApi.getPopularTvShows()

    override suspend fun getMovies(): List<MovieEntity> = mApi.getMovies()

    override suspend fun getMovieVideos(movieID: Int): List<VideoEntity>? = mApi.getMovieVideos(movieID)

    override suspend fun getTvShowVideos(thShowID: Int): List<VideoEntity>? = mApi.getTvShowVideos(thShowID)
}