package com.riders.thelabdesk.core.domain.di

import com.riders.thelabdesk.core.domain.repository.IRepository
import com.riders.thelabdesk.core.domain.usecase.NewsUseCase

interface DomainContainer {
    val newsUseCase: NewsUseCase
}

class DomainContainerImpl(private val repository: IRepository) : DomainContainer {
    override val newsUseCase: NewsUseCase by lazy {
        NewsUseCase(repository)
    }
}
