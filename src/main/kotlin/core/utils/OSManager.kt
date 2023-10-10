package core.utils

import java.util.*

object OSManager {
    fun getOperatingSystem(): String {
        // System.out.println("Using System Property: " + os);
        return System.getProperty("os.name")
    }

    fun getSystemLocale(): String {
        return Locale.getDefault().displayLanguage
    }
}