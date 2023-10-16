package ui.settings

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.compose.component.TheLabDeskSwitch
import core.compose.component.TheLabDeskText
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

    TheLabDeskTheme {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            state = state
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TheLabDeskText(
                        modifier = Modifier,
                        text = if (viewModel.isDarkMode) "Disable dark mode" else "Enable dark mode"
                    )
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        TheLabDeskSwitch(viewModel)
                    }
                    //Switch(checked = viewModel.isDarkMode, onCheckedChange = viewModel::updateDarkMode)
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
    viewModel.updateDarkMode(true)
    TheLabDeskTheme {
        SettingsContent(viewModel = viewModel)
    }
}
