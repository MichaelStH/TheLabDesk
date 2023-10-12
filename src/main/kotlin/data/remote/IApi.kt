package data.remote

import data.remote.dto.NewsDto

interface IApi {

    suspend fun getNews(): List<NewsDto>
}