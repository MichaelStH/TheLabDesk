package di

import data.IRepository
import data.RepositoryImpl
import data.remote.ApiImpl

object AppModule {

    @Synchronized
    fun injectDependencies(): IRepository {
        val repositoryImpl = RepositoryImpl(ApiImpl())
        return repositoryImpl as IRepository
    }
}