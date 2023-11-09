import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.sun.javafx.application.PlatformImpl
import com.toxicbakery.logging.Arbor
import com.toxicbakery.logging.Seedling
import core.compose.component.AppTitleBar
import core.compose.component.ScrollableWindowContent
import core.compose.component.TheLabDeskSurface
import core.compose.component.video.initializeMediaPlayerComponent
import core.compose.theme.TheLabDeskTheme
import core.compose.utils.WindowDraggableArea
import core.log.Timber
import core.utils.DisplayManager
import core.utils.FileManager
import core.utils.SystemManager
import data.local.bean.WindowTypes
import di.AppModule
import ui.About
import ui.Exit
import ui.browser.BrowserViewModel
import ui.home.HomeViewModel
import ui.main.App
import ui.main.MainViewModel
import ui.news.NewsViewModel
import ui.splashscreen.SplashScreen
import ui.theaters.TheaterTeaserContent
import ui.theaters.TheatersViewModel
import utils.Constants
import java.awt.Dimension
import java.util.*

object TheLabDeskApp {

    var isVlcFound: Boolean = false

    private val versionProperties = Properties()
    fun getVersion(): String = versionProperties.getProperty("version") ?: "no version"

    fun init() {
        Timber.d("init()")
        runCatching {
            versionProperties.load(this.javaClass.getResourceAsStream("generated-version/version.properties"))
        }
            .onFailure {
                Timber.e("init | runCatching | onFailure: ${it.message}")
            }
            .onSuccess {
                Timber.d("init | runCatching | onSuccess")
            }

        Timber.d("version: ${getVersion()}")

        // Check if VLC Library is present
        checkVlcLibrary()
    }

    /**
     * Source https://github.com/ToxicBakery/Arbor
     */
    fun initArbor() {
        Arbor.sow(Seedling())
        Timber.d("initArbor()")
    }

    private fun checkVlcLibrary() {
        Timber.d("checkVlcLibrary()")
        initializeMediaPlayerComponent()
    }

    fun updateVlcFoundLibrary(isVlcFound: Boolean) {
        this.isVlcFound = isVlcFound
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
    TheLabDeskApp.init()

    val viewModel = MainViewModel(AppModule.injectDependencies())
    val homeViewModel = HomeViewModel()
    val newsViewModel = NewsViewModel(AppModule.injectDependencies())
    val browserViewModel = BrowserViewModel()
    val theatersViewModel = TheatersViewModel(AppModule.injectDependencies())

    // viewModel.getTime()
    viewModel.updateDarkMode(true)

    FileManager.createConfigFile()

    /*GlobalScope.launch {
        while (isActive) {
            val newMode = isSystemInDarkTheme()
            if (viewModel.isDarkMode != newMode) {
                viewModel.updateDarkMode(newMode)
            }
            delay(1_000)
        }
    }*/
    application(exitProcessOnExit = true) {
        // Required to make sure the JavaFx event loop doesn't finish (can happen when java fx panels in app are shown/hidden)
        val finishListener = object : PlatformImpl.FinishListener {
            override fun idle(implicitExit: Boolean) {}
            override fun exitCalled() {}
        }
        PlatformImpl.addListener(finishListener)

        val screenWidth: Int = DisplayManager.getScreenWidth()
        val screenHeight: Int = DisplayManager.getScreenHeight()

        Timber.d("application | java.awt.Toolkit.getDefaultToolkit().screenSize | width: ${screenWidth}, height: $screenHeight")

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
                icon = painterResource(
                    resourcePath = if (SystemManager.isMacOs()) "icons/thelab_desk.icns"
                    else if (SystemManager.isLinux()) "icons/thelab_desk.png"
                    else "icons/thelab_desk.ico"
                ),
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
            transparent = false,
            resizable = viewModel.windowType != WindowTypes.SPLASHSCREEN,
            onCloseRequest = {
                PlatformImpl.removeListener(finishListener)
                exitApplication()
            }
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
                                            modifier = Modifier.blur(radius = if (viewModel.shouldShowAboutDialog || viewModel.shouldExitAppConfirmationDialog || theatersViewModel.showTheaterItemTeaserVideo) 25.dp else 0.dp)
                                        ) {
                                            // App Content
                                            App(window, viewModel, homeViewModel, newsViewModel,browserViewModel, theatersViewModel)
                                        }
                                        if (viewModel.shouldShowAboutDialog) {
                                            About(viewModel)
                                        }

                                        if (viewModel.shouldExitAppConfirmationDialog) {
                                            Exit(viewModel)
                                        }

                                        if(theatersViewModel.showTheaterItemTeaserVideo){
                                            TheaterTeaserContent(theatersViewModel)
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
            Timber.d("LaunchedEffect | viewModel.shouldExitApp | value: ${viewModel.shouldExitApp}")
            if (viewModel.shouldExitApp) {
                exitApplication()
            }
        }

        LaunchedEffect(viewModel.isDarkMode) {
            Timber.d("LaunchedEffect | viewModel.isDarkMode | value: ${viewModel.isDarkMode}")
            FileManager.updateConfigFile(Pair(Constants.IS_DARK_MODE, viewModel.isDarkMode))
        }
    }
}
