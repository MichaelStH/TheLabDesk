package ui

import TheLabDeskApp
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import core.compose.theme.TheLabDeskTheme
import di.AppModule
import viewmodel.MainViewModel

@Composable
fun News(viewModel: MainViewModel) {
    TheLabDeskTheme {

    }
}

@Preview
@Composable
private fun PreviewNews() {
    TheLabDeskTheme {
        News(MainViewModel(AppModule.injectDependencies()))
    }
}