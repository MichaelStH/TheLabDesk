package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import core.log.Timber
import data.local.bean.WindowTypes

class MainViewModel {

    var windowType by mutableStateOf(WindowTypes.SPLASHSCREEN)
        private set

    var isLoadingFinished by mutableStateOf(false)
        private set

    var isDarkMode by mutableStateOf(false)
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
    }
}