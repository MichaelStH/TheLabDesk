package com.riders.thelabdesk.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import com.riders.thelabdesk.core.common.log.Timber
import com.riders.thelabdesk.core.domain.repository.IRepository
import com.riders.thelabdesk.core.ui.base.BaseViewModel
import com.riders.thelabdesk.core.ui.data.local.bean.WindowTypes
import com.riders.thelabdesk.core.ui.data.local.compose.IslandUiState
import data.local.model.compose.NavigationUiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*

class TheLabDeskViewModel(
    private val repository: IRepository
) : BaseViewModel() {


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

    private val _navigationOptions = NavigationUiState.values().toMutableStateList()
    val navigationOptions: List<NavigationUiState>
        get() = _navigationOptions

    private var _currentNavigationUiState: MutableStateFlow<NavigationUiState> =
        MutableStateFlow(NavigationUiState.Home)
    val currentNavigationUiState: StateFlow<NavigationUiState> = _currentNavigationUiState


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


    fun updateKeyboardVisible(isVisible: Boolean) {
        keyboardVisible = isVisible
    }

    fun updateIsDynamicIslandVisible(visible: Boolean) {
        isDynamicIslandVisible = visible
    }

    fun updateSearchApp(requestedAppName: String) {
        searchedAppRequest = requestedAppName
    }

    fun updateIsSearchFocused(focused: Boolean) {
        isSearchFocused = focused
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

    //////////////////////////////////////////
    // Coroutines
    //////////////////////////////////////////
    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            Timber.tag("MainViewModel")
                .e("CoroutineExceptionHandler | Error caught with message : ${throwable.message}")
        }


    /////////////////////////////////////////////////////
    //
    // OVERRIDE METHODS
    //
    /////////////////////////////////////////////////////
    init {
        Timber.d("Init ViewModel")

        updateNavigationItemSelected(NavigationUiState.Home)
    }

    /////////////////////////////////////////////////////
    //
    // CLASS METHODS
    //
    /////////////////////////////////////////////////////
    fun onBaseEvent(baseEvent: com.riders.thelabdesk.core.ui.base.UiEvent) {
        when (baseEvent) {
            else -> {
                Timber.e("onEvent() | Unhandled base event : $baseEvent")
            }
        }
    }

    fun onEvent(event: UiEvent) {
        when (event) {
            else -> {
                Timber.e("onEvent() | Unhandled event : $event")
            }
        }
    }


    /** Get Time in order to force dark mode or not */
    fun getTime() {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        Timber.d("getTime() | hour: ${hour.toString()}")

        if (hour !in 8..17) {
            Timber.d("hour NOT in range should force dark mode")
            updateDarkMode(true)
        }
    }
}