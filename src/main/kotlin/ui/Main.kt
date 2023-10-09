package ui

import TheLabDeskApp
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import core.compose.component.AppTitleBar
import core.compose.utils.WindowDraggableArea
import core.log.Timber

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    var icon = Icons.Filled.Check

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
        }) {
            Text(text)
        }
    }
}

fun main() = application {

    // Init Timber Logging
    TheLabDeskApp.initArbor()
    Timber.d("main() | applicationScope")

    val windowState: WindowState = rememberWindowState(width = 850.dp, height = 600.dp)

    Window(
        state = windowState,
        undecorated = true,
        //transparent = true,
        resizable = false,
        onCloseRequest = ::exitApplication) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Custom title toolbar
            WindowDraggableArea(modifier = Modifier.fillMaxWidth()) {
                AppTitleBar(windowState) {
                    exitApplication()
                }
            }

            // App Content
            App()
        }
    }
}
