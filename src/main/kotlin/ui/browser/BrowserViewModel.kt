package ui.browser

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import base.BaseViewModel
import com.sun.javafx.application.PlatformImpl
import core.log.Timber
import javafx.application.Platform
import javafx.scene.web.WebEngine
import javafx.scene.web.WebView
import utils.Constants

class BrowserViewModel : BaseViewModel() {

    var webView: WebView? by mutableStateOf(null)
        private set
    var engine: WebEngine? by mutableStateOf(null)
        private set
    var currentUrl: String by mutableStateOf(Constants.URL_GOOGLE)
        private set
    var isSearchFocused: Boolean by mutableStateOf(false)
        private set

    var javaThread: Platform? by mutableStateOf(null)
        private set

    fun updateCurrentUrl(newUrl: String) {
        Timber.d("updateCurrentUrl() | newUrl: $newUrl")
        this.currentUrl = newUrl
    }

    fun updateIsSearchFocused(isFocused: Boolean) {
        Timber.d("updateIsSearchFocused() | isFocused: $isFocused")
        this.isSearchFocused = isFocused
    }

    fun updateWebView(webView: WebView) {
        Timber.d("updateWebView() | webView: $webView")
        this.webView = webView
    }

    fun updateWebEngine() {
        if (null == webView) {
            Timber.e("updateWebEngine() | webview is null")
            return
        }

        Timber.d("updateWebEngine() | webView engine: ${webView?.engine}")
        this.engine = webView?.engine
    }

    fun updateJavaThread(javaThread: Platform) {
        Timber.d("updateJavaThread() | javaThread: ${javaThread.toString()}")
        this.javaThread = javaThread
    }

    init {
        PlatformImpl.startup { Timber.d("init() | javaFX started") }
    }

    fun search() {
        Timber.d("search() | url: $currentUrl")
        // if (Platform.isFxApplicationThread())
        Platform.runLater {
            engine?.load(currentUrl)
        }
    }
}