package data

import data.remote.ApiImpl
import data.remote.dto.NewsDto
import data.remote.dto.tmdb.TMDBMovieResponse
import data.remote.dto.tmdb.TMDBTvShowsResponse
import data.remote.dto.tmdb.TMDBVideoResponse

class RepositoryImpl(apiImpl: ApiImpl) : IRepository {
    private val mApiImpl: ApiImpl = apiImpl
    override suspend fun getNews(): List<NewsDto> = mApiImpl.getNews()
    override suspend fun getTrendingMovies(): TMDBMovieResponse = mApiImpl.getTrendingMovies()

    override suspend fun getPopularMovies(): TMDBMovieResponse = mApiImpl.getPopularMovies()

    override suspend fun getUpcomingMovies(): TMDBMovieResponse = mApiImpl.getUpcomingMovies()

    override suspend fun getTrendingTvShows(): TMDBTvShowsResponse = mApiImpl.getTrendingTvShows()

    override suspend fun getPopularTvShows(): TMDBTvShowsResponse = mApiImpl.getPopularTvShows()

    override suspend fun getMovies(): TMDBMovieResponse = mApiImpl.getMovies()

    override suspend fun getMovieVideos(movieID: Int): TMDBVideoResponse? = mApiImpl.getMovieVideos(movieID)

    override suspend fun getTvShowVideos(thShowID: Int): TMDBVideoResponse? = mApiImpl.getTvShowVideos(thShowID)
}