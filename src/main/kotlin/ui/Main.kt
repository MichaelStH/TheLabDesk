package ui

import TheLabDeskApp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import core.compose.component.AppTitleBar
import core.compose.component.ScrollableWindowContent
import core.compose.utils.WindowDraggableArea
import core.log.Timber
import viewmodel.MainViewModel
import java.awt.Dimension

fun main() = application {
    val viewModel: MainViewModel = MainViewModel()

    // Init Timber Logging
    TheLabDeskApp.initArbor()
    Timber.d("main() | applicationScope")

    val windowState: WindowState = rememberWindowState(width = 850.dp, height = 600.dp)

    Window(
        state = windowState,
        icon = painterResource("images/ic_lab.png"),
        undecorated = true,
        //transparent = true,
        resizable = true,
        onCloseRequest = ::exitApplication
    ) {
        window.minimumSize = Dimension(800, 600)

        Column(modifier = Modifier.fillMaxSize()) {
            // Custom title toolbar
            WindowDraggableArea(modifier = Modifier.fillMaxWidth()) {
                AppTitleBar(viewModel, windowState) {
                    exitApplication()
                }
            }

            ScrollableWindowContent {
                // App Content
                App(viewModel)
            }
        }
    }
}
