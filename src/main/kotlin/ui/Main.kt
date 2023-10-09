package ui

import TheLabDeskApp
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import core.compose.component.AppTitleBar
import core.compose.component.ScrollableWindowContent
import core.compose.utils.AsyncBitmapImageFromNetwork
import core.compose.utils.WindowDraggableArea
import core.log.Timber
import utils.Constants
import viewmodel.MainViewModel

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
        Column(modifier = Modifier.fillMaxSize()) {
            // Custom title toolbar
            WindowDraggableArea(modifier = Modifier.fillMaxWidth()) {
                AppTitleBar(windowState) {
                    exitApplication()
                }
            }

            // App Content
            App(viewModel)
        }
    }
}
