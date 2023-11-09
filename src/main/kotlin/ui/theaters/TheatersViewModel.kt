package ui.theaters

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import base.BaseViewModel
import core.log.Timber
import data.IRepository
import data.local.model.compose.MoviesUiState
import data.local.model.compose.TMDBViewState
import data.local.model.compose.TheatersUiState
import data.local.model.tmdb.MovieModel
import data.local.model.tmdb.TDMBTeaserModel
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
            ?.let { errorState ->
                updateMoviesUiState(errorState)
                updateTvShowsUiState(errorState)
            }
    }

    //////////////////////////////
    // Composable States
    //////////////////////////////
    private var _theatersUiState: MutableStateFlow<TheatersUiState> = MutableStateFlow(TheatersUiState.Loading)
    val theatersUiState: StateFlow<TheatersUiState> = _theatersUiState

    private var _tmdbViewState: MutableStateFlow<TMDBViewState> = MutableStateFlow(TMDBViewState.Movies)
    val tmdbViewState: StateFlow<TMDBViewState> = _tmdbViewState

    private var _movieUiState: MutableStateFlow<MoviesUiState> = MutableStateFlow(MoviesUiState.None)
    val movieUiState: StateFlow<MoviesUiState> = _movieUiState

    private var _tvShowsUiState: MutableStateFlow<MoviesUiState> = MutableStateFlow(MoviesUiState.None)
    val tvShowsUiState: StateFlow<MoviesUiState> = _tvShowsUiState

    var tmdbTeaser: TDMBTeaserModel? by mutableStateOf(null)
        private set
    var showTheaterItemTeaserVideo: Boolean by mutableStateOf(false)
        private set

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

    var theaterTypeSelected: Int by mutableStateOf(0)
        private set
    var tabIconSelected: Int by mutableStateOf(0)
        private set
    var isStaggeredMode: Boolean by mutableStateOf(false)
        private set

    fun updateTheatersUiState(newState: TheatersUiState) {
        this._theatersUiState.value = newState
    }

    fun updateTmdbTeaser(tmdbTeaser: TDMBTeaserModel?) {
        this.tmdbTeaser = tmdbTeaser
    }

    fun updateMoviesUiState(newState: MoviesUiState) {
        this._movieUiState.value = newState

        if (newState is MoviesUiState.Success) {
            updateTheatersUiState(TheatersUiState.Success)
        }
        if (newState is MoviesUiState.Error) {
            updateTheatersUiState(TheatersUiState.Error(newState.message))
        }
    }

    fun updateTvShowsUiState(newState: MoviesUiState) {
        this._tvShowsUiState.value = newState

        if (newState is MoviesUiState.Success) {
            updateTheatersUiState(TheatersUiState.Success)
        }
        if (newState is MoviesUiState.Error) {
            updateTheatersUiState(TheatersUiState.Error(newState.message))
        }
    }

    fun updatePopularMovieList(list: List<MovieModel>) {
        this.popularMovieList = list
    }

    fun updateTrendingMovieList(list: List<MovieModel>) {
        this.trendingMovieList = list
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

        this._tmdbViewState.value = when (index) {
            0 -> {
                TMDBViewState.Movies
            }

            1 -> {
                TMDBViewState.TvShows
            }

            else -> {
                TMDBViewState.Movies
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

    fun updateShowTeaserVideo(showTeaser: Boolean) {
        this.showTheaterItemTeaserVideo = showTeaser
    }

    fun fetchTMDBData() {
        Timber.d("fetchTMDBData()")

        val result = runBlocking(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler) {
            val popularMoviesJob = async { repository.getPopularMovies() }
            val trendingMoviesJob = async { repository.getTrendingMovies() }
            val upcomingMoviesJob = async { repository.getUpcomingMovies() }
            val popularTvShowsJob = async { repository.getPopularTvShows() }
            val trendingTvShowsJob = async { repository.getTrendingTvShows() }

            withContext(Dispatchers.Default) {
                popularMoviesJob.await()
                    .results
                    .map { it.toModel() }
                    .let { updatePopularMovieList(it) }
            }

            withContext(Dispatchers.Default) {
                trendingMoviesJob.await()
                    .results
                    .map { it.toModel() }
                    .let { updateTrendingMovieList(it) }
            }

            withContext(Dispatchers.Default) {
                upcomingMoviesJob.await()
                    .results
                    .map { it.toModel() }
                    .let { updateUpcomingMovieList(it) }
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

            repository.getMovies()
        }

        updateMoviesUiState(MoviesUiState.Success(result))
        updateTvShowsUiState(MoviesUiState.Success(result))
    }

    fun getTMDBItemId(movieID: Int, name: String, isMovie: Boolean) {
        Timber.d("getTMDBItemId()")

        runBlocking(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler) {
            delay(2_500)

            val video = if (isMovie) repository.getMovieVideos(movieID) else repository.getTvShowVideos(movieID)
            val youtubeKey = video?.results?.find { it.type.contains("teaser", true) }

            youtubeKey?.let {
                // Youtube key found update value
                withContext(Dispatchers.Default) {
                    updateTmdbTeaser(TDMBTeaserModel(id = video.id, name = name, youtubeKey = it.key))
                }
            } ?: run {
                // Youtube key not found
                Timber.e("Youtube key not found")
            }
        }
    }
}