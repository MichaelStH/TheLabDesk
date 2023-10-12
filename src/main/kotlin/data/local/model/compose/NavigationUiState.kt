package data.local.model.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import data.local.bean.NavigationItemType

sealed class NavigationUiState(
    val icon: ImageVector,
    var selected: Boolean,
    val navigationItemType: NavigationItemType
) {
    data object Home : NavigationUiState(Icons.Filled.Home, false, NavigationItemType.DEFAULT)
    data object News : NavigationUiState(Icons.Filled.Newspaper, false, NavigationItemType.DEFAULT)
    data object Settings : NavigationUiState(Icons.Filled.Settings, false, NavigationItemType.SETTINGS)

}