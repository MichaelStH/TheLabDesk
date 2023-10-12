package core.utils

import core.log.Timber
import utils.Constants
import java.util.*

object SystemManager {
    fun getOperatingSystem(): String {
        return System.getProperty("os.name")
    }

    fun getVersion(): String {
        return System.getProperty("os.version")
    }

    fun getArchitecture(): String {
        return System.getProperty("os.arch")
    }

    fun getSystemLocale(): String {
        return Locale.getDefault().displayLanguage
    }

    fun getSystemInfo() {
        Timber.d("getSystemInfo()")

        Timber.d(
            "Application is running on:" + "\n" +
                    "name: ${getOperatingSystem()}" + "\n" +
                    "version: ${getVersion()}" + "\n" +
                    "architecture: ${getArchitecture()}" + "\n" +
                    "locale: ${getSystemLocale()}" + "\n" +
                    ""
        )
    }

    fun isWindows11(): Boolean = getOperatingSystem().equals(Constants.WINDOWS_11, true)
}