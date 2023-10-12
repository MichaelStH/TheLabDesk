package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import core.log.Timber
import data.local.bean.WindowTypes
import data.local.model.compose.NavigationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel {

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

        _navigationOptions.find { it == selectedOption }?.let {
            Timber.d("update selected item: ${it.toString()}, ${it.selected}, ${it.navigationItemType}")
            updateCurrentNavigationUiState(it)
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
}