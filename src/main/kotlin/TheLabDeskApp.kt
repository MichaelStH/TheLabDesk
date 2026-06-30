package com.riders.thelabdesk

import com.riders.thelabdesk.core.common.log.Timber
import com.riders.thelabdesk.core.common.utils.SystemManager
import com.riders.thelabdesk.core.ui.base.Application
import com.riders.thelabdesk.core.video.core.utils.VLCManager
import com.toxicbakery.logging.Arbor
import com.toxicbakery.logging.Seedling
import com.riders.thelabdesk.di.AppContainer
import com.riders.thelabdesk.di.AppContainerImpl
import java.util.*

class TheLabDeskApp private constructor() : Application() {

    lateinit var container: AppContainer
        private set

    var isVlcFound: Boolean = false

    private val versionProperties = Properties()
    fun getVersion(): String = versionProperties.getProperty("version") ?: "no version"

    fun init() {
        Timber.d("init()")
        container = AppContainerImpl()

        initTimber()

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

        if (SystemManager.isMacOs()) {
            System.setProperty("VLC_PLUGIN_PATH", "/Applications/VLC.app/Contents/MacOS/plugins")
        }

        // Check if VLC Library is present
        checkVlcLibrary()
    }

    fun initTimber() {
        // Init Timber Logging. Source https://github.com/ToxicBakery/Arbor
        Arbor.sow(Seedling())
        Timber.d("initArbor()")
        Timber.d("main() | applicationScope")

        SystemManager.getSystemInfo()
    }

    private fun checkVlcLibrary() {
        Timber.d("checkVlcLibrary()")
        VLCManager.initializeMediaPlayerComponent { found ->
            updateVlcFoundLibrary(found)
        }
    }

    fun updateVlcFoundLibrary(isVlcFound: Boolean) {
        this.isVlcFound = isVlcFound
    }

    companion object {
        @Volatile
        private var instance: TheLabDeskApp? = null

        @Synchronized
        fun getInstance(): TheLabDeskApp = instance ?: synchronized(this) {
            instance ?: TheLabDeskApp().also { instance = it }
        }
    }
}