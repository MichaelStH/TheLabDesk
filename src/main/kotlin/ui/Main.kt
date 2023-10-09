package ui

import TheLabDeskApp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import core.compose.component.AppTitleBar
import core.compose.component.ScrollableWindowContent
import core.compose.component.TheLabDeskIcon
import core.compose.theme.TheLabDeskTheme
import core.compose.utils.WindowDraggableArea
import core.log.Timber
import viewmodel.MainViewModel
import java.awt.Dimension

fun main() = application {

    // Init Timber Logging
    TheLabDeskApp.initArbor()
    Timber.d("main() | applicationScope")

    val windowState: WindowState = rememberWindowState(width = 850.dp, height = 600.dp)
    val viewModel: MainViewModel = MainViewModel()

    var isOpen by remember { mutableStateOf(true) }

    if (isOpen) {
        val trayState = rememberTrayState()
        val notification = rememberNotification("Notification", "Message from MyApp!")

        Tray(
            state = trayState,
            icon = TheLabDeskIcon,
            menu = {
                Item(
                    "Send notification",
                    onClick = {
                        trayState.sendNotification(notification)
                    }
                )
                Item(
                    "Exit",
                    onClick = {
                        isOpen = false
                    }
                )
            }
        )
    }

    Window(
        state = windowState,
        icon = painterResource("images/ic_lab.png"),
        undecorated = true,
        //transparent = true,
        resizable = true,
        onCloseRequest = ::exitApplication
    ) {
        window.minimumSize = Dimension(800, 600)

        TheLabDeskTheme {
            Column(modifier = Modifier.fillMaxSize()) {
                // Custom title toolbar
                WindowDraggableArea(modifier = Modifier.fillMaxWidth()) {
                    AppTitleBar(viewModel, windowState) {
                        exitApplication()
                    }
                }

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    ScrollableWindowContent(
                        modifier = Modifier.blur(radius = if (viewModel.shouldShowAboutDialog || viewModel.shouldExitAppConfirmationDialog) 25.dp else 0.dp)
                    ) {
                        // App Content
                        App(viewModel)
                    }
                    if (viewModel.shouldShowAboutDialog) {
                        About(viewModel)
                    }

                    if (viewModel.shouldExitAppConfirmationDialog) {
                        Exit(viewModel)
                    }
                }
            }

        }
    }
}
