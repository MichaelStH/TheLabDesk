package core.utils

import com.github.tkuenneth.nativeparameterstoreaccess.MacOSDefaults
import com.github.tkuenneth.nativeparameterstoreaccess.WindowsRegistry
import core.log.Timber
import utils.Constants
import java.io.File
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

    fun getUserDirectory(): String {
        return if (getOperatingSystem().contains("win", true)) {
            System.getProperty("user.home") + File.separator + "Documents" + File.separator
        } else if (getOperatingSystem().contains("mac")) {
            System.getProperty("user.home") + File.separator
        } else {
            System.getProperty("user.home") + File.separator
        }
    }

    fun getSystemInfo() {
        Timber.d("getSystemInfo()")

        Timber.d(
            "Application is running on:" + "\n" +
                    "       name: ${getOperatingSystem()}" + "\n" +
                    "       version: ${getVersion()}" + "\n" +
                    "       architecture: ${getArchitecture()}" + "\n" +
                    "       locale: ${getSystemLocale()}" + "\n" +
                    "       user home path: ${getUserDirectory()}" + "\n" +
                    ""
        )
    }


    fun isWindows(): Boolean = System.getProperty("os.name").lowercase(Locale.getDefault()).contains("win")
    fun isWindows11(): Boolean = getOperatingSystem().equals(Constants.WINDOWS_11, true)
    fun isMacOs(): Boolean = System.getProperty("os.name").lowercase(Locale.getDefault()).contains("mac")

    fun isSystemInDarkTheme(): Boolean {
        Timber.d("isSystemInDarkTheme()")
        return when {
            SystemManager.isWindows() -> {
                val result = WindowsRegistry.getWindowsRegistryEntry(
                    "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize",
                    "AppsUseLightTheme"
                )
                result == 0x0
            }

            SystemManager.isMacOs() -> {
                val result = MacOSDefaults.getDefaultsEntry("AppleInterfaceStyle")
                result == "Dark"
            }

            else -> false
        }
    }
}