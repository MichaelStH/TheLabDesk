package core.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import core.compose.component.toast.ToastViewModel.showToast
import core.log.Timber
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object ToastManager {

    var _show: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val show: StateFlow<Boolean> = _show

    var toastMessage: String by mutableStateOf("")
        private set

    fun show(message: String) {
        Timber.d("show() | shown: $showToast, message: $message")
        if(_show.value) {
            _show.value = false
        }
        _show.value = true

        toastMessage = message
    }

    fun hide() {
        Timber.d("hide()")
        _show.value = false
        toastMessage = ""
    }
}