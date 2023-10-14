package di

import core.log.Timber
import data.IRepository
import data.RepositoryImpl
import data.remote.ApiImpl

object AppModule {

    fun injectDependencies(): IRepository {
        Timber.d("injectDependencies()")
        val repositoryImpl = RepositoryImpl(ApiImpl())
        return repositoryImpl as IRepository
    }
}