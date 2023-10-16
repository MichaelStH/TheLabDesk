package ui.main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.compose.theme.TheLabDeskTheme
import di.AppModule
import viewmodel.MainViewModel


//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@Composable
@Preview
fun App(viewModel: MainViewModel) {
    TheLabDeskTheme(viewModel.isDarkMode) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(modifier = Modifier.width(100.dp).fillMaxHeight(), contentAlignment = Alignment.Center) {
                    NavigationBar(viewModel)
                }
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                    NavigationContent(viewModel)
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
private fun PreviewApp() {
    val viewModel: MainViewModel = MainViewModel(AppModule.injectDependencies())
    TheLabDeskTheme {
        App(viewModel = viewModel)
    }
}
