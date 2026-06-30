package com.riders.thelabdesk.di

import com.riders.thelabdesk.core.data.di.DataContainer
import com.riders.thelabdesk.core.data.di.DataContainerImpl
import com.riders.thelabdesk.core.domain.di.DomainContainer
import com.riders.thelabdesk.core.domain.di.DomainContainerImpl
import com.riders.thelabdesk.feature.browser.BrowserViewModel
import com.riders.thelabdesk.feature.home.ui.HomeViewModel
import com.riders.thelabdesk.feature.news.ui.NewsViewModel
import com.riders.thelabdesk.feature.splashscreen.SplashScreenViewModel
import com.riders.thelabdesk.feature.theaters.ui.TheatersViewModel
import com.riders.thelabdesk.ui.TheLabDeskViewModel

interface AppContainer {
    val dataContainer: DataContainer
    val domainContainer: DomainContainer

    val mainViewModel: TheLabDeskViewModel
    val splashScreenViewModel: SplashScreenViewModel
    val homeViewModel: HomeViewModel
    val newsViewModel: NewsViewModel
    val browserViewModel: BrowserViewModel
    val theatersViewModel: TheatersViewModel
}

class AppContainerImpl : AppContainer {
    override val dataContainer: DataContainer by lazy {
        DataContainerImpl()
    }

    override val domainContainer: DomainContainer by lazy {
        DomainContainerImpl(dataContainer.repository)
    }

    override val mainViewModel: TheLabDeskViewModel
        get() = TheLabDeskViewModel(repository = dataContainer.repository)

    override val splashScreenViewModel: SplashScreenViewModel
        get() = SplashScreenViewModel()

    override val homeViewModel: HomeViewModel
        get() = HomeViewModel()

    override val newsViewModel: NewsViewModel
        get() = NewsViewModel(newsUseCase = domainContainer.newsUseCase)

    override val browserViewModel: BrowserViewModel
        get() = BrowserViewModel()

    override val theatersViewModel: TheatersViewModel
        get() = TheatersViewModel(repository = dataContainer.repository)
}
