package com.riders.thelabdesk.feature.theaters.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.riders.thelabdesk.core.common.log.Timber
import com.riders.thelabdesk.core.domain.entities.tmdb.MovieEntity
import com.riders.thelabdesk.core.domain.entities.tmdb.TeaserEntity
import com.riders.thelabdesk.core.domain.entities.tmdb.TvShowsEntity
import com.riders.thelabdesk.core.domain.repository.IRepository
import com.riders.thelabdesk.core.ui.base.BaseViewModel
import com.riders.thelabdesk.core.ui.utils.ToastManager
import com.riders.thelabdesk.feature.theaters.data.local.compose.MoviesUiState
import com.riders.thelabdesk.feature.theaters.data.local.compose.TMDBViewState
import com.riders.thelabdesk.feature.theaters.data.local.compose.TheatersUiState
import com.riders.thelabdesk.feature.theaters.data.local.model.TMDBModelCompose
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TheatersViewModel(private val repository: IRepository) : BaseViewModel() {

    //////////////////////////////
    // Coroutine
    //////////////////////////////
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        Timber.e("CoroutineExceptionHandler | Error caught with message : ${throwable.message}")
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

    var tmdbTeaser: TeaserEntity? by mutableStateOf(null)
        private set
    var showTheaterItemTeaserVideo: Boolean by mutableStateOf(false)
        private set

    var trendingMovieList: List<MovieEntity> by mutableStateOf(emptyList())
        private set

    var popularMovieList: List<MovieEntity> by mutableStateOf(emptyList())
        private set
    var upcomingMovieList: List<MovieEntity> by mutableStateOf(emptyList())
        private set

    var trendingTvShowsList: List<TvShowsEntity> by mutableStateOf(emptyList())
        private set
    var popularTvShowsList: List<TvShowsEntity> by mutableStateOf(emptyList())
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

    fun updateTmdbTeaser(tmdbTeaser: TeaserEntity?) {
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

    fun updatePopularMovieList(list: List<MovieEntity>) {
        this.popularMovieList = list
    }

    fun updateTrendingMovieList(list: List<MovieEntity>) {
        this.trendingMovieList = list
    }

    fun updateUpcomingMovieList(list: List<MovieEntity>) {
        this.upcomingMovieList = list
    }

    fun updateTrendingTvShowsList(list: List<TvShowsEntity>) {
        this.trendingTvShowsList = list
    }

    fun updatePopularTvShowsList(list: List<TvShowsEntity>) {
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
                updatePopularMovieList(popularMoviesJob.await())
            }

            withContext(Dispatchers.Default) {
                updateTrendingMovieList(trendingMoviesJob.await())
            }

            withContext(Dispatchers.Default) {
                updateUpcomingMovieList(upcomingMoviesJob.await())
            }


            withContext(Dispatchers.Default) {
                updatePopularTvShowsList(popularTvShowsJob.await())
            }

            withContext(Dispatchers.Default) {
                updateTrendingTvShowsList(trendingTvShowsJob.await())
            }

            repository.getMovies()
        }

       // updateMoviesUiState(MoviesUiState.Success(result))
       // updateTvShowsUiState(MoviesUiState.Success(result))
    }

    fun getTMDBItemId(movieID: Int, name: String, isMovie: Boolean) {
        Timber.d("getTMDBItemId()")

        runBlocking(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler) {
            delay(2_500)

            val video = if (isMovie) repository.getMovieVideos(movieID) else repository.getTvShowVideos(movieID)

            if (video?.isEmpty() == true) {
                updateShowTeaserVideo(false)
                ToastManager.show("No video for $name item")
            }

            val youtubeKey =
                video?.find { it.type.contains("teaser", true) || it.type.contains("trailer", true) }

            youtubeKey?.let {
                // Youtube key found update value
                withContext(Dispatchers.Default) {
                    updateTmdbTeaser(TeaserEntity(id = it.id.toInt(), name = name, youtubeKey = it.key))
                }
            } ?: run {
                // Youtube key not found
                Timber.e("Youtube key not found")
            }
        }
    }
}