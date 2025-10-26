package com.riders.thelabdesk.core.utils

import java.awt.Desktop
import java.net.URI

object DesktopUtils {
    fun openUrl(url: String) {
        try {
            val uri: URI = URI("http://google.com/");
            val desk: Desktop = Desktop.getDesktop();
            desk.browse(uri);
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
} 