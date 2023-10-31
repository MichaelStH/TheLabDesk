package ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import base.BaseViewModel
import core.log.Timber
import data.IRepository
import data.local.bean.WindowTypes
import data.local.model.compose.IslandUiState
import data.local.model.compose.NavigationUiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(private val repository: IRepository) : BaseViewModel() {

    //////////////////////////////////////////
    // Coroutines
    //////////////////////////////////////////
    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            Timber.e("${throwable.message}")
        }


    //////////////////////////////////////////
    // Compose states
    //////////////////////////////////////////
    var isDynamicIslandVisible by mutableStateOf(false)
        private set
    var searchedAppRequest by mutableStateOf("")
    var isSearchFocused by mutableStateOf(false)
        private set

    var keyboardVisible by mutableStateOf(false)

    val dynamicIslandState = mutableStateOf<IslandUiState>(IslandUiState.DefaultState())
    fun displayDynamicIsland(isDisplayed: Boolean) {
        dynamicIslandState.value = IslandUiState.SearchState()
    }

    var windowType by mutableStateOf(WindowTypes.SPLASHSCREEN)
        private set

    var isLoadingFinished by mutableStateOf(false)
        private set

    /*var isDarkMode by mutableStateOf(false)
        private set*/

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
                        if (shouldShowAboutDialog) {
                            updateShouldShowAboutDialog(false)
                        }
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
                        if (shouldExitAppConfirmationDialog) {
                            updateShowExitConfirmation(false)
                        }
                        updateShouldShowAboutDialog(true)
                    })
                })
        )
    }

    var currentIndex by mutableStateOf(0)
    var previousIndex by mutableStateOf(0)
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

    /*fun updateDarkMode(isDark: Boolean) {
        this.isDarkMode = isDark
        isDarkTheme = isDark
    }*/

    fun updateNavigationItemSelected(selectedOption: NavigationUiState) {
        _navigationOptions.forEach { it.selected = false }
        _navigationOptions.find { it == selectedOption }?.selected = true

        _navigationOptions.find { it == selectedOption }?.let { navigationItem ->
            currentIndex = NavigationUiState.getIndexOf(navigationItem)
            Timber.d("update selected item: ${navigationItem.toString()}, ${navigationItem.selected}, ${navigationItem.navigationItemType}")
            updateCurrentNavigationUiState(navigationItem)

            _navigationOptions.forEach { element ->
                Timber.d("list updated: ${element.toString()}, ${element.selected}, ${element.navigationItemType}")
            }
            previousIndex = currentIndex
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

    fun updateKeyboardVisible(isVisible: Boolean) {
        keyboardVisible = isVisible
    }

    fun updateIsDynamicIslandVisible(visible: Boolean) {
        isDynamicIslandVisible = visible
    }

    fun updateIsSearchFocused(focused: Boolean) {
        isSearchFocused = focused
    }

    init {
        Timber.d("Init ViewModel")

        updateNavigationItemSelected(NavigationUiState.Home)
    }

    //////////////////////////////////
    //
    // CLASS METHODS
    //
    //////////////////////////////////
    fun searchApp(requestedAppName: String) {
        searchedAppRequest = requestedAppName
    }
}