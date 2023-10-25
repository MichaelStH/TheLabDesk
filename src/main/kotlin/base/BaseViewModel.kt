package base

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import core.compose.theme.isDarkTheme
import core.log.Timber
import java.util.*

abstract class BaseViewModel {
    var isDarkMode by mutableStateOf(false)
        private set

    fun updateDarkMode(isDark: Boolean) {
        this.isDarkMode = isDark
        isDarkTheme = isDark
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