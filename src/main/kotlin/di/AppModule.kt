package di

import core.log.Timber
import data.IRepository
import data.RepositoryImpl
import data.remote.ApiImpl

object AppModule {

    fun providesApi(): ApiImpl = ApiImpl().also {
        Timber.d("providesApi()")
    }

    fun providesRepository(apiImpl: ApiImpl = providesApi()): IRepository = run {
        RepositoryImpl(apiImpl = apiImpl) as IRepository
    }.also {
        Timber.d("providesRepository()")
    }

    fun injectDependencies(): IRepository {
        Timber.d("injectDependencies()")
        val repositoryImpl = RepositoryImpl(ApiImpl())
        return repositoryImpl as IRepository
    }
}