package ui.theaters

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import base.BaseViewModel
import core.log.Timber
import data.IRepository
import data.local.model.compose.MoviesUiState
import data.local.model.compose.TheatersUiState
import data.local.model.tmdb.MovieModel
import data.local.model.tmdb.TvShowsModel
import data.remote.dto.tmdb.toModel
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


    var trendingMovieList: List<MovieModel> by mutableStateOf(emptyList())
        private set

    var popularMovieList: List<MovieModel> by mutableStateOf(emptyList())
        private set
    var upcomingMovieList: List<MovieModel> by mutableStateOf(emptyList())
        private set

    var trendingTvShowsList: List<TvShowsModel> by mutableStateOf(emptyList())
        private set
    var popularTvShowsList: List<TvShowsModel> by mutableStateOf(emptyList())
        private set

    var theaterTypeSelected by mutableStateOf(0)
        private set
    var tabIconSelected by mutableStateOf(0)
        private set
    var isStaggeredMode by mutableStateOf(false)
        private set

    fun updateTheatersUiState(newState: TheatersUiState) {
        this._theatersUiState.value = newState
    }

    fun updateMoviesUiState(newState: MoviesUiState) {
        this._movieUiState.value = newState
    }

    fun updateTvShowsUiState(newState: MoviesUiState) {
        this._tvShowsUiState.value = newState
    }

    fun updatePopularMovieList(list: List<MovieModel>) {
        this.popularMovieList = list
    }

    fun updateUpcomingMovieList(list: List<MovieModel>) {
        this.upcomingMovieList = list
    }

    fun updateTrendingTvShowsList(list: List<TvShowsModel>) {
        this.trendingTvShowsList = list
    }

    fun updatePopularTvShowsList(list: List<TvShowsModel>) {
        this.popularTvShowsList = list
    }

    fun updateTheaterTypeSelected(index: Int) {
        this.theaterTypeSelected = index

        this._theatersUiState.value = when (index) {
            0 -> {
                TheatersUiState.Movies
            }

            1 -> {
                TheatersUiState.TvShows
            }

            else -> {
                TheatersUiState.Movies
            }
        }
    }

    fun updateTabIconSelected(index: Int) {
        this.tabIconSelected = index

        updateIsStaggeredMode(if (0 == index) false else if (1 == index) true else false)
    }

    fun updateIsStaggeredMode(isStaggered: Boolean) {
        this.isStaggeredMode = isStaggered
    }

    fun <T> clearList(list: MutableList<T>) {
        if (list.isNotEmpty()) {
            list.clear()
        }
    }

    fun fetchTrendingMovies() {
        Timber.d("fetchTrendingMovies()")
        val list = runBlocking(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler) {
            repository.getTrendingMovies()
        }

        Timber.d("result: ${list.results.size}")

        if (list.results.isNotEmpty()) {
            trendingMovieList = list.results.map { it.toModel() }
            updateMoviesUiState(MoviesUiState.Success(list))
        }
    }

    fun fetchPopularMovies() {
        Timber.d("fetchPopularMovies()")
        val list = runBlocking(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler) {
            repository.getPopularMovies()
        }

        Timber.d("result: ${list.results.size}")

        if (list.results.isNotEmpty()) {
            val modelList = list.results.map { it.toModel() }
            updatePopularMovieList(modelList)
        }
    }

    fun fetchUpcomingMovies() {
        Timber.d("fetchUpcomingMovies()")
        val list = runBlocking(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler) {
            repository.getUpcomingMovies()
        }

        Timber.d("result: ${list.results.size}")

        if (list.results.isNotEmpty()) {
            val modelList = list.results.map { it.toModel() }
            updateUpcomingMovieList(modelList)
        }
    }

    fun fetchTrendingTvShows() {
        Timber.d("fetchTrendingTvShows()")
        val list = runBlocking(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler) {
            repository.getTrendingTvShows()
        }

        Timber.d("result: ${list.results.size}")

        if (list.results.isNotEmpty()) {
            val modelList = list.results.map { it.toModel() }
            updateTrendingTvShowsList(modelList)
        }
    }

    fun fetchPopularTvShows() {
        Timber.d("fetchPopularTvShows()")
        val list = runBlocking(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler) {
            repository.getPopularTvShows()
        }

        Timber.d("result: ${list.results.size}")

        if (list.results.isNotEmpty()) {
            val modelList = list.results.map { it.toModel() }
            updatePopularTvShowsList(modelList)
        }
    }


    fun fetchTMDBData() {
        Timber.d("fetchTMDBData()")

        val result = runBlocking(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler) {
            val popularMoviesJob = async { repository.getPopularMovies() }
            val trendingMoviesJob = launch { repository.getTrendingMovies() }
            val upcomingMoviesJob = launch { repository.getUpcomingMovies() }
            val popularTvShowsJob = async { repository.getPopularTvShows() }
            val trendingTvShowsJob = async { repository.getTrendingTvShows() }

            withContext(Dispatchers.Default) {
                popularMoviesJob.await()
                    .results
                    .map { it.toModel() }
                    .let { updatePopularMovieList(it) }
            }


            withContext(Dispatchers.Default) {
                popularTvShowsJob.await()
                    .results
                    .map { it.toModel() }
                    .let { updatePopularTvShowsList(it) }
            }

            withContext(Dispatchers.Default) {
                trendingTvShowsJob.await()
                    .results
                    .map { it.toModel() }
                    .let { updateTrendingTvShowsList(it) }
            }

            joinAll(
                popularMoviesJob,
                trendingMoviesJob,
                upcomingMoviesJob,
                popularTvShowsJob,
                trendingTvShowsJob
            )

            repository.getMovies()
        }

        Timber.d("result: ${result.results.size}")

        if (result.results.isNotEmpty()) {
            updateMoviesUiState(MoviesUiState.Success(result))
            updateTvShowsUiState(MoviesUiState.Success(result))
        }
    }
}