package com.riders.thelabdesk.feature.splashscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.riders.thelabdesk.core.common.log.Timber
import com.riders.thelabdesk.core.ui.base.BaseViewModel
import com.riders.thelabdesk.core.ui.data.local.bean.WindowTypes

class SplashScreenViewModel : BaseViewModel() {

    var windowType by mutableStateOf(WindowTypes.SPLASHSCREEN)
        private set

    var isLoadingFinished by mutableStateOf(false)
        private set

    fun updateWindowType(newType: WindowTypes) {
        this.windowType = newType
    }

    fun updateIsLoading(loadingFinished: Boolean) {
        this.isLoadingFinished = loadingFinished

        if (loadingFinished) {
            Timber.d("Loading finished")
            updateWindowType(WindowTypes.MAIN)
        }
    }
}