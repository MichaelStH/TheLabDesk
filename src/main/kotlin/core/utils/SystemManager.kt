package core.utils

import com.github.tkuenneth.nativeparameterstoreaccess.MacOSDefaults
import com.github.tkuenneth.nativeparameterstoreaccess.WindowsRegistry
import core.log.Timber
import utils.Constants
import java.awt.Desktop
import java.io.File
import java.net.URI
import java.util.*

object SystemManager {

    /**
     * @return operating system's name
     */
    fun getOperatingSystem(): String {
        return System.getProperty("os.name")
    }

    /**
     * @return operating system's name using default value
     */
    fun getOperatingSystem(defaultValue: String): String {
        return System.getProperty("os.name", defaultValue)
    }

    /**
     * @return system's version
     */
    fun getVersion(): String {
        return System.getProperty("os.version")
    }

    /**
     * @return system's architecture
     */
    fun getArchitecture(): String {
        return System.getProperty("os.arch")
    }


    /**
     * @return system's locale
     */
    fun getSystemLocale(): String {
        return Locale.getDefault().displayLanguage
    }

    /**
     * @return user's home directory (i.e.: for Windows users : "C:/Users/username/Documents" ; for mac and Linux users : /User/username/~)
     */
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


    /**
     * Check if current system is Windows
     * @return true if it is Windows, false if not
     */
    fun isWindows(): Boolean = getOperatingSystem().lowercase(Locale.getDefault()).contains("win")

    /**
     * Check if current system is Windows 11
     * @return true if it is Windows 11, false if not
     */
    fun isWindows11(): Boolean = getOperatingSystem().equals(Constants.WINDOWS_11, true)


    /**
     * Check if current system is macOS
     * @return true if it is macOS, false if not
     */
    fun isMacOs(): Boolean = getOperatingSystem().lowercase(Locale.getDefault()).contains("mac")
    fun isMacOs(defaultValue: String): Boolean = getOperatingSystem(defaultValue)
            .lowercase(Locale.ENGLISH)
            .run { "mac" in this || "darwin" in this }


    /**
     * Check if current system is macOS
     * @return true if it is macOS, false if not
     */
    fun isLinux(): Boolean =
        getOperatingSystem().lowercase(Locale.getDefault()).contains("deb") ||
                getOperatingSystem().lowercase(Locale.getDefault()).contains("linux") ||
                getOperatingSystem().lowercase(Locale.getDefault()).contains("ubuntu")

    fun isSystemInDarkTheme(): Boolean {
        Timber.d("isSystemInDarkTheme()")
        return when {
            isWindows() -> {
                val result = WindowsRegistry.getWindowsRegistryEntry(
                    "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize",
                    "AppsUseLightTheme"
                )
                result == 0x0
            }

            isMacOs() -> {
                val result = MacOSDefaults.getDefaultsEntry("AppleInterfaceStyle")
                result == "Dark"
            }

            else -> false
        }
    }

    /**
     * Open link into a web browser
     *
     * @param url target url to open in browser
     */
    fun openInBrowser(url: String) {
        runCatching {
            val osName by lazy(LazyThreadSafetyMode.NONE) { getOperatingSystem().lowercase(Locale.getDefault()) }
            val desktop = Desktop.getDesktop()
            val uri = URI.create(url)

            Timber.d("openInBrowser() | osName: $osName | uri: $uri")

            when {
                Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE) -> desktop.browse(uri)
                "mac" in osName -> Runtime.getRuntime().exec("open $uri")
                "nix" in osName || "nux" in osName -> Runtime.getRuntime().exec("xdg-open $uri")
                else -> throw RuntimeException("cannot open $uri")
            }
        }
            .onFailure {
                Timber.e("openInBrowser | runCatching | onFailure: ${it.message}")
            }
            .onSuccess {
                Timber.d("openInBrowser | runCatching | onSuccess")
            }

    }
}