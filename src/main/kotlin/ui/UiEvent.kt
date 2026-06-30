package com.riders.thelabdesk.ui

sealed interface UiEvent {

    data class OnShowAboutDialog(val show: Boolean) : UiEvent
}