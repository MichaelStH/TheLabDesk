package com.riders.thelabdesk.core.ui.base

sealed interface UiEvent {


    // Dynamic Island
    data class OnUpdateIsSearchFocused(val isFocused: Boolean) : UiEvent
    data class OnUpdateSearch(val newSearchValue: String) : UiEvent
    data class OnUpdateKeyboardVisible(val isKeyboardVisible: Boolean) : UiEvent
}