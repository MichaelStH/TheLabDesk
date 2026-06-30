package com.riders.thelabdesk.core.ui.base

sealed interface UiEvent {
    data class OnUpdateDarkMode(val isDarkMode: Boolean) : UiEvent
    data class OnUpdateIsDynamicIslandVisible(val isVisible: Boolean) : UiEvent


    // Dynamic Island
    data class OnUpdateIsSearchFocused(val isFocused: Boolean) : UiEvent
    data class OnUpdateSearch(val newSearchValue: String) : UiEvent
    data class OnUpdateKeyboardVisible(val isKeyboardVisible: Boolean) : UiEvent
}