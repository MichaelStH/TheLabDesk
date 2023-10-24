package data

import data.remote.ApiImpl
import data.remote.dto.NewsDto
import data.remote.dto.tmdb.TMDBResponse

class RepositoryImpl(apiImpl: ApiImpl) : IRepository {
    private val mApiImpl: ApiImpl = apiImpl
    override suspend fun getNews(): List<NewsDto> = mApiImpl.getNews()
    override suspend fun getMovies(): TMDBResponse = mApiImpl.getMovies()
}