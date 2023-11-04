package core.compose.component.browser

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.round
import javafx.application.Platform
import javafx.concurrent.Worker
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.web.WebEngine
import javafx.scene.web.WebView
import netscape.javascript.JSObject
import ui.browser.BrowserViewModel
import java.awt.BorderLayout
import javax.swing.JPanel

@Composable
fun Browser(composeWindow: ComposeWindow, modifier: Modifier, url: String) {
    val jfxPanel = remember { JFXPanel() }
    var jsObject = remember<JSObject?> { null }

    BoxWithConstraints(modifier = modifier) {
        ComposeJFXPanel(
            composeWindow = composeWindow,
            jfxPanel = jfxPanel,
            onCreate = {
                Platform.runLater {
                    val root = WebView()
                    val engine: WebEngine = root.engine
                    val scene: Scene = Scene(root)

                    // Set the user agent to simulate a browser for YouTube
                    engine.userAgent =
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"

                    // Enable JavaScript support for YouTube embed player
                    engine.isJavaScriptEnabled = true

                    engine.loadWorker.stateProperty().addListener { _, _, newState ->
                        if (newState === Worker.State.SUCCEEDED) {
                            jsObject = root.engine.executeScript("window") as JSObject
                            // execute other javascript / setup js callbacks fields etc..
                        }
                    }
                    engine.loadWorker.exceptionProperty().addListener { _, _, newError ->
                        println("page load error : $newError")
                    }
                    jfxPanel.scene = scene
                    engine.load(url) // can be a html document from resources ..
                    engine.setOnError { error -> println("onError : $error") }
                }
            }, onDestroy = {
                Platform.runLater {
                    jsObject?.let { jsObj ->
                        // clean up code for more complex implementations i.e. removing javascript callbacks etc..
                    }
                }
            })
    }
}

@Composable
fun Browser(composeWindow: ComposeWindow, viewModel: BrowserViewModel, modifier: Modifier, url: String) {
    val jfxPanel = remember { JFXPanel() }
    var jsObject = remember<JSObject?> { null }

    BoxWithConstraints(modifier = modifier) {
        ComposeJFXPanel(
            composeWindow = composeWindow,
            jfxPanel = jfxPanel,
            onCreate = {
                Platform.runLater {
                    viewModel.updateWebView(WebView())
                    viewModel.updateWebEngine()
                    val scene = Scene(viewModel.webView)

                    viewModel.engine?.let {

                        // Set the user agent to simulate a browser for YouTube
                        it.userAgent =
                            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"

                        // Enable JavaScript support for YouTube embed player
                        it.isJavaScriptEnabled = true

                        it.loadWorker.stateProperty().addListener { _, _, newState ->
                            if (newState === Worker.State.SUCCEEDED) {
                                jsObject = viewModel.webView!!.engine.executeScript("window") as JSObject
                                // execute other javascript / setup js callbacks fields etc..
                            }
                        }
                        it.loadWorker.exceptionProperty().addListener { _, _, newError ->
                            println("page load error : $newError")
                        }
                        jfxPanel.scene = scene
                        it.load(url) // can be a html document from resources ..
                        it.setOnError { error -> println("onError : $error") }
                    }

                        // viewModel.updateJavaThread(Platform)
                }
            }, onDestroy = {
                Platform.runLater {
                    jsObject?.let { jsObj ->
                        // clean up code for more complex implementations i.e. removing javascript callbacks etc..
                    }
                }
            })
    }
}

@Composable
fun ComposeJFXPanel(
    composeWindow: ComposeWindow,
    jfxPanel: JFXPanel,
    onCreate: () -> Unit,
    onDestroy: () -> Unit = {}
) {
    val jPanel = remember { JPanel() }
    val density = LocalDensity.current.density

    Layout(
        content = {},
        modifier = Modifier.onGloballyPositioned { childCoordinates ->
            val coordinates = childCoordinates.parentCoordinates!!
            val location = coordinates.localToWindow(Offset.Zero).round()
            val size = coordinates.size
            jPanel.setBounds(
                (location.x / density).toInt(),
                (location.y / density).toInt(),
                (size.width / density).toInt(),
                (size.height / density).toInt()
            )
            jPanel.validate()
            jPanel.repaint()
        },
        measurePolicy = { _, _ -> layout(0, 0) {} })

    DisposableEffect(jPanel) {
        composeWindow.add(jPanel)
        jPanel.layout = BorderLayout(0, 0)
        jPanel.add(jfxPanel)
        onCreate()
        onDispose {
            onDestroy()
            composeWindow.remove(jPanel)
        }
    }
}

@Composable
fun DesktopWebView(
    modifier: Modifier,
    url: String,
) {
    val jPanel: JPanel = remember { JPanel() }
    val jfxPanel: JFXPanel = remember { JFXPanel() }

    SwingPanel(
        factory = {
            jfxPanel.apply { buildWebView(url) }
            jPanel.add(jfxPanel)
        },
        modifier = modifier,
    )

    DisposableEffect(url) { onDispose { jPanel.remove(jfxPanel) } }
}

private fun JFXPanel.buildWebView(url: String) {

    Platform.runLater {
        val webView = WebView()
        val webEngine = webView.engine

        // Set the user agent to simulate a browser for YouTube
        webEngine.userAgent =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"

        // Enable JavaScript support for YouTube embed player
        webEngine.isJavaScriptEnabled = true

        // Load the YouTube video using the embed URL
        webEngine.load(url)
        val scene = Scene(webView)
        setScene(scene)
    }
}

class JFXWebView : JFXPanel() {
    init {
        Platform.runLater(::initialiseJavaFXScene)
    }

    private fun initialiseJavaFXScene() {
        val webView = WebView()
        val webEngine = webView.engine
        webEngine.load("https://html5test.com/")
        val scene = Scene(webView)
        setScene(scene)
    }
}