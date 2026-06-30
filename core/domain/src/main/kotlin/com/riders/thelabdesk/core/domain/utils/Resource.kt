package com.riders.thelabdesk.core.domain.utils

sealed interface Resource<out T> {

    data object Loading : Resource<Nothing>

    data class Error(val message: String, val cause: Throwable? = null) : Resource<Nothing>

    data class Success<T>(val data: T) : Resource<T>
}