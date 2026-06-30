package com.riders.thelabdesk.core.domain.usecase

import com.riders.thelabdesk.core.domain.entities.news.NewsEntity
import com.riders.thelabdesk.core.domain.repository.IRepository
import com.riders.thelabdesk.core.domain.utils.Resource
import com.riders.thelabdesk.core.domain.utils.UseCase

class NewsUseCase(private val repository: IRepository) : UseCase<List<NewsEntity>> {
    override suspend fun invoke(params: Any): Resource<List<NewsEntity>?> {
        return runCatching {
            val response = repository.getNews()
            Resource.Success(response)
        }
            .onFailure { exception -> exception.printStackTrace() }
            .getOrElse { exception -> Resource.Error(message = exception.message!!, cause = exception) }
    }
}