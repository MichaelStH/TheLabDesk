package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class MainViewModel {

    var menuOptions: Set<Pair<String, Set<String>>> = buildSet {
        add(
            Pair(
                "File",
                buildSet {
                    add("New Window..")
                    add("Exit")
                })
        )
        add(
            Pair(
                "Help",
                buildSet {
                    add("About")
                })
        )
    }

    var text by mutableStateOf("Hello, World!")
        private set
    var search by mutableStateOf("")
        private set

    fun updateText(newValue: String) {
        this.text = newValue
    }

    fun updateSearch(newSearchText: String) {
        this.search = newSearchText
    }
}