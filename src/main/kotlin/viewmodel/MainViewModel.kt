package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import core.log.Timber
import data.IRepository
import data.local.bean.WindowTypes
import data.local.model.compose.NavigationUiState
import data.local.model.compose.NewsUiState
import data.remote.dto.NewsDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking

class MainViewModel(private val repository: IRepository) {

    var windowType by mutableStateOf(WindowTypes.SPLASHSCREEN)
        private set

    var isLoadingFinished by mutableStateOf(false)
        private set

    var isDarkMode by mutableStateOf(false)
        private set

    private val _navigationOptions = NavigationUiState.values().toMutableStateList()
    val navigationOptions: List<NavigationUiState>
        get() = _navigationOptions

    private var _currentNavigationUiState: MutableStateFlow<NavigationUiState> =
        MutableStateFlow(NavigationUiState.Home)
    val currentNavigationUiState: StateFlow<NavigationUiState> = _currentNavigationUiState

    var menuOptions: Set<Pair<String, Set<Pair<String, () -> Unit>>>> = buildSet {
        add(
            Pair(
                "File",
                buildSet {
                    add(Pair("New Window..") {
                        Timber.d("onClick() : New Window")
                    })
                    add(Pair("Exit") {
                        Timber.d("onClick() : Exit")
                        updateShowExitConfirmation(true)
                    })
                })
        )
        add(
            Pair(
                "Help",
                buildSet {
                    add(Pair("About") {
                        Timber.d("onClick() : About")
                        updateShouldShowAboutDialog(true)
                    })
                })
        )
    }

    var text by mutableStateOf("Hello, World!")
        private set
    var search by mutableStateOf("")
        private set
    var shouldShowAboutDialog by mutableStateOf(false)
        private set
    var shouldExitAppConfirmationDialog by mutableStateOf(false)
        private set
    var shouldExitApp by mutableStateOf(false)
        private set

    fun updateWindowType(newType: WindowTypes) {
        this.windowType = newType
    }

    fun updateIsLoading(loadingFinished: Boolean) {
        this.isLoadingFinished = loadingFinished

        if (loadingFinished) {
            Timber.d("Loading finished")
            updateWindowType(WindowTypes.MAIN)
        }
    }

    fun updateDarkMode(isDark: Boolean) {
        this.isDarkMode = isDark
    }

    fun updateNavigationItemSelected(selectedOption: NavigationUiState) {
        _navigationOptions.forEach { it.selected = false }
        _navigationOptions.find { it == selectedOption }?.selected = true

        _navigationOptions.find { it == selectedOption }?.let { navigationItem ->
            Timber.d("update selected item: ${navigationItem.toString()}, ${navigationItem.selected}, ${navigationItem.navigationItemType}")
            updateCurrentNavigationUiState(navigationItem)
            _navigationOptions.forEach { element ->
                Timber.d("list updated: ${element.toString()}, ${element.selected}, ${element.navigationItemType}")
            }
        }
    }

    fun updateCurrentNavigationUiState(newState: NavigationUiState) {
        this._currentNavigationUiState.value = newState
    }

    fun updateText(newValue: String) {
        this.text = newValue
    }

    fun updateSearch(newSearchText: String) {
        this.search = newSearchText
    }

    fun updateShouldShowAboutDialog(showAboutDialog: Boolean) {
        this.shouldShowAboutDialog = showAboutDialog
    }

    fun updateShowExitConfirmation(showExitConfirmation: Boolean) {
        this.shouldExitAppConfirmationDialog = showExitConfirmation
    }

    fun updateShouldExitApp(exitApp: Boolean) {
        this.shouldExitApp = exitApp
    }

    init {
        Timber.d("Init ViewModel")

        updateNavigationItemSelected(NavigationUiState.Home)
    }


    ////////////////////////////////////////////
    // News
    ////////////////////////////////////////////
    private var _newsUiState: MutableStateFlow<NewsUiState> = MutableStateFlow(NewsUiState.Loading)
    val newsUiState: StateFlow<NewsUiState> = _newsUiState

    fun updateNewsUiState(newState: NewsUiState) {
        this._newsUiState.value = newState
    }

    fun fetchNews() {
        Timber.d("fetchNews()")

        val list: List<NewsDto>? = runBlocking {
            runCatching {
                repository.getNews()
            }
                .onFailure { throwable ->
                    Timber.e("runCatching | onFailure | ${throwable.message}")
                    throwable.message
                        ?.let { NewsUiState.Error(it) }
                        ?.let { errorState -> updateNewsUiState(errorState) }
                }
                .onSuccess {
                    Timber.d("runCatching | onSuccess | done")
                }
                .getOrNull()
        }

        Timber.d("result: $list")

        if (!list.isNullOrEmpty()) {
            updateNewsUiState(NewsUiState.Success(list))
        }
    }
}