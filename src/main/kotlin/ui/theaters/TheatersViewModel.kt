package ui.theaters

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import base.BaseViewModel
import core.log.Timber
import data.IRepository
import data.local.model.compose.MoviesUiState
import data.local.model.compose.TheatersUiState
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
    private var _theatersUiState: MutableStateFlow<TheatersUiState> = MutableStateFlow(TheatersUiState.Movies)
    val theatersUiState: StateFlow<TheatersUiState> = _theatersUiState

    private var _movieUiState: MutableStateFlow<MoviesUiState> = MutableStateFlow(MoviesUiState.None)
    val movieUiState: StateFlow<MoviesUiState> = _movieUiState

    private var _tvShowsUiState: MutableStateFlow<MoviesUiState> = MutableStateFlow(MoviesUiState.None)
    val tvShowsUiState: StateFlow<MoviesUiState> = _tvShowsUiState

    var selected by mutableStateOf(0)
        private set
    var isStaggeredMode by mutableStateOf(false)
        private set

    fun updateTheatersUiState(newState: TheatersUiState) {
        this._theatersUiState.value = newState
    }
    fun updateMoviesUiState(newState: MoviesUiState) {
        this._movieUiState.value = newState
    }

    fun updateSelected(index: Int) {
        this.selected = index

        this._theatersUiState.value = when(index) {
            0 ->{TheatersUiState.Movies}
            1->{TheatersUiState.TvShows}
            else->{TheatersUiState.Movies}
        }
    }
    fun updateIsStaggeredMode(isStaggered:Boolean) {
        this.isStaggeredMode = isStaggered
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

    fun fetchTvShows() {
        Timber.d("fetchTvShows()")
    }
}