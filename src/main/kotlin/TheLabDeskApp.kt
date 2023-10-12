import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.toxicbakery.logging.Arbor
import com.toxicbakery.logging.Seedling
import core.compose.component.AppTitleBar
import core.compose.component.ScrollableWindowContent
import core.compose.component.TheLabDeskIcon
import core.compose.theme.TheLabDeskTheme
import core.compose.utils.WindowDraggableArea
import core.log.Timber
import core.utils.DisplayManager
import core.utils.SystemManager
import data.IRepository
import data.RepositoryImpl
import data.local.bean.WindowTypes
import data.remote.ApiImpl
import di.AppModule
import ui.About
import ui.App
import ui.Exit
import ui.SplashScreen
import viewmodel.MainViewModel
import java.awt.Dimension


object TheLabDeskApp {

    @Synchronized
    fun init() {

    }

    /**
     * Source https://github.com/ToxicBakery/Arbor
     */
    fun initArbor() {
        Arbor.sow(Seedling())
        Timber.d("initArbor()")
    }
}

//////////////////////////////////////////
//
// CLASS METHODS
//
//////////////////////////////////////////
fun initTimber() {
    // Init Timber Logging
    TheLabDeskApp.initArbor()
    Timber.d("main() | applicationScope")

    SystemManager.getSystemInfo()
}


//////////////////////////////////////////
//
// APP
//
//////////////////////////////////////////
fun main() {
    initTimber()

    val viewModel: MainViewModel = MainViewModel(AppModule.injectDependencies())


    val isDarkTheme = viewModel.isDarkMode

    application {
        val windowSize: Dimension = DisplayManager.getScreenDimension()
        val screenWidth: Int = windowSize.width
        val screenHeight: Int = windowSize.height

        Timber.d("java.awt.Toolkit.getDefaultToolkit().screenSize | width: ${screenWidth}, height: $screenHeight")

        val windowState: WindowState = rememberWindowState(
            width = 500.dp,
            height = 350.dp
        )

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
            transparent = true,
            resizable = viewModel.windowType != WindowTypes.SPLASHSCREEN,
            onCloseRequest = ::exitApplication
        ) {
            TheLabDeskTheme(isDarkTheme) {
                Box(modifier = Modifier.background(Color.Transparent)) {
                    AnimatedContent(
                        targetState = viewModel.isLoadingFinished,
                        transitionSpec = { fadeIn() togetherWith fadeOut() }
                    ) { target ->
                        if (!target) {
                            windowState.position = WindowPosition(x = (screenWidth / 3).dp, y = (screenHeight / 3).dp)
                            windowState.size = DpSize(width = 400.dp, height = 200.dp)

                            BoxWithConstraints(
                                modifier = Modifier
                                    .size(width = screenWidth.dp, height = screenHeight.dp)
                                    .background(Color.Transparent),
                                contentAlignment = Alignment.Center
                            ) {
                                Timber.d("Window size | width: ${this.maxWidth}, height: ${this.maxHeight}")
                                SplashScreen(viewModel)
                            }
                        } else {
                            // windowState.position = WindowPosition(x = (screenWidth / 6).dp, y = (screenHeight / 6).dp)
                            windowState.size = DpSize(width = 1200.dp, height = 800.dp)

                            if (!SystemManager.isWindows11()) {
                                Column(modifier = Modifier.fillMaxSize()) {
                                    // Custom title toolbar
                                    WindowDraggableArea(modifier = Modifier.fillMaxWidth()) {
                                        AppTitleBar(
                                            viewModel = viewModel,
                                            windowState = windowState
                                        ) {
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
                            } else {
                                Card(shape = RoundedCornerShape(12.dp)) {
                                    Column(modifier = Modifier.fillMaxSize()) {
                                        // Custom title toolbar
                                        WindowDraggableArea(modifier = Modifier.fillMaxWidth()) {
                                            AppTitleBar(
                                                viewModel = viewModel,
                                                windowState = windowState
                                            ) {
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
                    }
                }
            }
        }

        LaunchedEffect(viewModel.shouldExitApp) {
            if (viewModel.shouldExitApp) {
                exitApplication()
            }
        }
    }
}
