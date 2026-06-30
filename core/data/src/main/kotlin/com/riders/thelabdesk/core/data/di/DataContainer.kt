package com.riders.thelabdesk.core.data.di

import com.riders.thelabdesk.core.data.repository.ApiImpl
import com.riders.thelabdesk.core.data.repository.RepositoryImpl
import com.riders.thelabdesk.core.domain.repository.IRepository
import com.riders.thelabdesk.core.domain.repository.remote.IApi

interface DataContainer {
    val api: IApi
    val repository: IRepository
}

class DataContainerImpl : DataContainer {
    override val api: IApi by lazy {
        ApiImpl()
    }

    override val repository: IRepository by lazy {
        RepositoryImpl(api)
    }
}
