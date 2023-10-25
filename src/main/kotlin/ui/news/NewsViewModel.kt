package ui.news

import base.BaseViewModel
import core.log.Timber
import data.IRepository
import data.local.model.compose.NewsUiState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NewsViewModel(private val repository: IRepository) : BaseViewModel() {

    //////////////////////////////
    // Coroutine
    //////////////////////////////
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e("Error caught: ${throwable.message}")
        throwable.message
            ?.let { NewsUiState.Error(it) }
            ?.let { errorState -> updateNewsUiState(errorState) }
    }

    //////////////////////////////
    // Composable States
    //////////////////////////////
    private var _newsUiState: MutableStateFlow<NewsUiState> = MutableStateFlow(NewsUiState.Loading)
    val newsUiState: StateFlow<NewsUiState> = _newsUiState

    fun updateNewsUiState(newState: NewsUiState) {
        this._newsUiState.value = newState
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchNews() {
        Timber.d("fetchNews()")

        GlobalScope.launch(Dispatchers.IO) {
            val list = runBlocking(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler) {
                repository.getNews()
            }

            Timber.d("result: $list")

            if (list.isNotEmpty()) {
                withContext(Dispatchers.Default) {
                    updateNewsUiState(NewsUiState.Success(list))
                }
            }
        }
    }
}