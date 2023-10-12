package data

import data.remote.ApiImpl
import data.remote.dto.NewsDto

class RepositoryImpl(apiImpl: ApiImpl) : IRepository {
    private val mApiImpl: ApiImpl = apiImpl
    override suspend fun getNews(): List<NewsDto> = mApiImpl.getNews()
}