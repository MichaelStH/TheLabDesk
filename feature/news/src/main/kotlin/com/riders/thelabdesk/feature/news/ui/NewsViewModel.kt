package com.riders.thelabdesk.feature.news.ui

import com.riders.thelabdesk.core.common.log.Timber
import com.riders.thelabdesk.core.domain.utils.Resource
import com.riders.thelabdesk.core.domain.usecase.NewsUseCase
import com.riders.thelabdesk.core.ui.base.BaseViewModel
import com.riders.thelabdesk.feature.news.data.local.model.compose.NewsUiState
import com.riders.thelabdesk.feature.news.data.local.model.toUiModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NewsViewModel(
    private val newsUseCase: NewsUseCase
) : BaseViewModel() {

    //////////////////////////////
    // Composable States
    //////////////////////////////
    private var _newsUiState: MutableStateFlow<NewsUiState> = MutableStateFlow(NewsUiState.Loading)
    val newsUiState: StateFlow<NewsUiState> = _newsUiState

    fun updateNewsUiState(newState: NewsUiState) {
        this._newsUiState.value = newState
    }

    //////////////////////////////
    // Coroutine
    //////////////////////////////
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.tag("NewsViewModel").e("Error caught: ${throwable.message}")
        throwable.message
            ?.let { NewsUiState.Error(it) }
            ?.let { errorState -> updateNewsUiState(errorState) }
    }


    @OptIn(DelicateCoroutinesApi::class)
    suspend fun fetchNews() {
        Timber.d("fetchNews()")
        when (val result = newsUseCase.invoke(Any())) {
            is Resource.Loading -> updateNewsUiState(NewsUiState.Loading)
            is Resource.Error -> updateNewsUiState(NewsUiState.Error(result.message))
            is Resource.Success -> {
                Timber.d("fetchNews() | result: ${result.data}")
                updateNewsUiState(NewsUiState.Success(result.data!!.map { it.toUiModel() }))
            }
        }
    }
}