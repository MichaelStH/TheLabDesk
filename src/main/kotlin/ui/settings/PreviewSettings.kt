package ui.settings

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.compose.theme.TheLabDeskTheme
import core.log.Timber
import di.AppModule
import viewmodel.MainViewModel


//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@Composable
fun SettingsContent(viewModel: MainViewModel) {
    Timber.d("recomposition : SettingsContent()")

    val state = rememberLazyListState()

    TheLabDeskTheme(viewModel.isDarkMode) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp), state = state) {
            item {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = if(viewModel.isDarkMode) "Unable dark mode" else "Enable dark mode")
                    Switch(checked = viewModel.isDarkMode, onCheckedChange = viewModel::updateDarkMode)
                }
            }
        }
    }
}

//////////////////////////////////////////////////
//
// PREVIEWS
//
//////////////////////////////////////////////////
@Preview
@Composable
private fun PreviewSettingsContent() {
    val viewModel: MainViewModel = MainViewModel(AppModule.injectDependencies())
    TheLabDeskTheme {
        SettingsContent(viewModel = viewModel)
    }
}
