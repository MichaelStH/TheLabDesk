package com.riders.thelabdesk.core.ui.base

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.riders.thelabdesk.core.common.log.Timber

abstract class BaseViewModel : DefaultLifecycleObserver {

    var isDarkMode by mutableStateOf(false)
        private set

    fun updateDarkMode(isDark: Boolean) {
        this.isDarkMode = isDark
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Timber.d("BaseViewModel | onCreate()")
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        Timber.e("BaseViewModel | onPause()")

    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Timber.d("BaseViewModel | onResume()")
    }

}