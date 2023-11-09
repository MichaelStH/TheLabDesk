package ui.browser

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import base.BaseViewModel
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
        this.currentUrl = newUrl
    }

    fun updateIsSearchFocused(isFocused: Boolean) {
        this.isSearchFocused = isFocused
    }

    fun updateWebView(webView: WebView) {
        this.webView = webView
    }

    fun updateWebEngine() {
        if (null == webView) {
            Timber.e("webview is null")
            return
        }

        this.engine = webView?.engine
    }

    fun updateJavaThread(javaThread: Platform) {
        this.javaThread = javaThread
    }

    fun search() {
        Timber.d("search() | url: $currentUrl")
        // if (Platform.isFxApplicationThread())
        Platform.runLater {
            engine?.load(currentUrl)
        }
    }
}