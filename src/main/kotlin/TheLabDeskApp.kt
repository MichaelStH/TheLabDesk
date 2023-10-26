import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.toxicbakery.logging.Arbor
import com.toxicbakery.logging.Seedling
import core.compose.component.*
import core.compose.theme.TheLabDeskTheme
import core.compose.utils.WindowDraggableArea
import core.log.Timber
import core.utils.DisplayManager
import core.utils.SystemManager
import data.local.bean.WindowTypes
import di.AppModule
import ui.About
import ui.Exit
import ui.home.HomeViewModel
import ui.main.App
import ui.main.MainViewModel
import ui.news.NewsViewModel
import ui.splashscreen.SplashScreen
import ui.theaters.TheatersViewModel
import java.awt.Dimension

object TheLabDeskApp {

    fun init() {
        Timber.d("init()")

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

    val viewModel = MainViewModel(AppModule.injectDependencies())
    val homeViewModel = HomeViewModel()
    val newsViewModel = NewsViewModel(AppModule.injectDependencies())
    val theatersViewModel = TheatersViewModel(AppModule.injectDependencies())

    // viewModel.getTime()
    viewModel.updateDarkMode(true)

    /*GlobalScope.launch {
        while (isActive) {
            val newMode = isSystemInDarkTheme()
            if (viewModel.isDarkMode != newMode) {
                viewModel.updateDarkMode(newMode)
            }
            delay(1_000)
        }
    }*/

    application {
        val screenWidth: Int = DisplayManager.getScreenWidth()
        val screenHeight: Int = DisplayManager.getScreenHeight()

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

            // Declaring Coroutine scope
            val scope = rememberCoroutineScope()
            val interactionSource = remember { MutableInteractionSource() }
            val isPressed by interactionSource.collectIsPressedAsState()
            val isFocus by interactionSource.collectIsFocusedAsState()
            val focusManager: FocusManager = LocalFocusManager.current
            val density = LocalDensity.current

            TheLabDeskTheme(viewModel.isDarkMode) {
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
                            window.minimumSize = Dimension(1000, 800)
                            windowState.position = WindowPosition(x = (screenWidth / 6).dp, y = (screenHeight / 6).dp)
                            windowState.size = DpSize(width = 1200.dp, height = 800.dp)

                            // A surface container using the 'background' color from the theme
                            TheLabDeskSurface(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(
                                        shape = if (!SystemManager.isWindows11()) RoundedCornerShape(0.dp) else RoundedCornerShape(
                                            12.dp
                                        )
                                    )
                            ) {

                                BoxWithConstraints(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.TopCenter
                                ) {

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .heightIn(0.dp, 120.dp)
                                            .padding(horizontal = 200.dp)
                                            .background(Color.Blue),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        /*// Dynamic Island
                                        AnimatedVisibility(
                                            modifier = Modifier.fillMaxWidth(),
                                            visible = if (LocalInspectionMode.current) true else viewModel.isDynamicIslandVisible,

                                            enter = slideInVertically {
                                                // Slide in from 40 dp from the top.
                                                with(density) { -40.dp.roundToPx() }
                                            } + fadeIn(
                                                // Fade in with the initial alpha of 0.3f.
                                                initialAlpha = 0.3f
                                            ),
                                            exit = slideOutVertically {
                                                // Slide in from 40 dp from the top.
                                                with(density) { -40.dp.roundToPx() }
                                            } + fadeOut()
                                        ) {
                                            DynamicIsland(
                                                viewModel = viewModel,
                                                modifier = Modifier.height(this@BoxWithConstraints.maxHeight - 8.dp)
                                                    .clip(shape = RoundedCornerShape(22.dp)),
                                                islandUiState = viewModel.dynamicIslandState.value
                                            )
                                        }*/
                                    }

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
                                            modifier = Modifier.fillMaxSize().pointerInput(isPressed) {
                                                if (viewModel.isDynamicIslandVisible) {
                                                    viewModel.updateIsDynamicIslandVisible(false)
                                                }
                                            },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            ScrollableWindowContent(
                                                modifier = Modifier.blur(radius = if (viewModel.shouldShowAboutDialog || viewModel.shouldExitAppConfirmationDialog) 25.dp else 0.dp)
                                            ) {
                                                // App Content
                                                App(viewModel, homeViewModel, newsViewModel, theatersViewModel)
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
