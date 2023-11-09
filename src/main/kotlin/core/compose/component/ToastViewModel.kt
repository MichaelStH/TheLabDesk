package core.compose.component

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import core.log.Timber
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object ToastViewModel {

    var _show: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val show: StateFlow<Boolean> = _show

    var showToast: Boolean by mutableStateOf(false)
        private set
    var toastMessage: String by mutableStateOf("")
        private set

    fun show(message: String) {
        Timber.d("show() | shown: $showToast, message: $message")
        this._show.value = true
        this.showToast = true
        this.toastMessage = message
    }

    fun hide() {
        Timber.d("hide()")
        this._show.value = false
        this.showToast = false
        this.toastMessage = ""
    }
}