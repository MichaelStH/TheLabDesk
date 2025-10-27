package com.riders.thelabdesk.core.compose.component.browser

import core.log.Timber
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.web.WebView
import kotools.types.text.NotBlankString

class JFXWebView(var url: NotBlankString? = null, val htmlData: NotBlankString? = null) : JFXPanel() {
    init {
        Platform.runLater(::initialiseJavaFXScene)
    }

    private fun initialiseJavaFXScene() {
        require(null != url || null != htmlData) {
            "url or htmlData is required"
        }

        Timber.d("initialiseJavaFXScene() | url: $url, htmlData: ${htmlData.toString().length}")

        val webViewData = url ?: htmlData

        val webView = WebView()
        val webEngine = webView.engine
        webEngine.load(webViewData.toString())
        val scene = Scene(webView)
        setScene(scene)
    }
}