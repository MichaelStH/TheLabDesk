package data.local.model.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import data.local.bean.NavigationItemType

// First lets create a class which will contain an icon, a type and a boolean initialSelectedValue,
// and its exposing a selected state variable which is mutableState of initialSelectedValue,
// this will enable to update individual rows in recomposition
sealed class NavigationUiState(
    val icon: ImageVector,
    var initialSelectedValue: Boolean,
    val navigationItemType: NavigationItemType
) {
    var selected by mutableStateOf(initialSelectedValue)

    data object Home : NavigationUiState(Icons.Filled.Home, false, NavigationItemType.DEFAULT)
    data object News : NavigationUiState(Icons.Filled.Newspaper, false, NavigationItemType.DEFAULT)
    data object Theaters : NavigationUiState(Icons.Filled.Movie, false, NavigationItemType.DEFAULT)
    data object Settings : NavigationUiState(Icons.Filled.Settings, false, NavigationItemType.SETTINGS)

    companion object {
        fun getIndexOf(item: NavigationUiState): Int = values().indexOf(item)
        fun find(state: NavigationUiState) =
            NavigationUiState::class.sealedSubclasses
                .map { it.objectInstance as NavigationUiState }
                .firstOrNull { it == state }
                .let {
                    when (it) {
                        null -> Home
                        else -> it
                    }
                }

        fun values(): List<NavigationUiState> = listOf(Home, News, Theaters, Settings)

        fun valuesIndexed(): List<NavigationUiState> =
            NavigationUiState::class.sealedSubclasses.mapIndexed { _, kClass -> kClass.objectInstance as NavigationUiState }
    }
}