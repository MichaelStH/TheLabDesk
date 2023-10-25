package ui.theaters

import base.BaseViewModel
import core.log.Timber
import data.IRepository
import data.local.model.compose.MoviesUiState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TheatersViewModel(private val repository: IRepository) : BaseViewModel() {

    //////////////////////////////
    // Coroutine
    //////////////////////////////
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e("Error caught: ${throwable.message}")
        throwable.message
            ?.let { MoviesUiState.Error(it) }
            ?.let { errorState -> updateMoviesUiState(errorState) }
    }

    //////////////////////////////
    // Composable States
    //////////////////////////////
    private var _movieUiState: MutableStateFlow<MoviesUiState> = MutableStateFlow(MoviesUiState.None)
    val movieUiState: StateFlow<MoviesUiState> = _movieUiState

    fun updateMoviesUiState(newState: MoviesUiState) {
        this._movieUiState.value = newState
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchMovies() {
        Timber.d("fetchMovies()")

        GlobalScope.launch(Dispatchers.IO) {
            val list = runBlocking(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler) {
                repository.getMovies()
            }

            Timber.d("result: ${list.results.size}")

            if (list.results.isNotEmpty()) {
                withContext(Dispatchers.Default) {
                    updateMoviesUiState(MoviesUiState.Success(list))
                }
            }
        }
    }
}