package ui.settings

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import ui.main.MainViewModel


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
            modifier = Modifier.fillMaxSize(),
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
    val viewModel = MainViewModel(AppModule.injectDependencies())
    viewModel.updateDarkMode(true)
    TheLabDeskTheme {
        SettingsContent(viewModel = viewModel)
    }
}
