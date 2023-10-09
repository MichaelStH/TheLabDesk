package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class MainViewModel {

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