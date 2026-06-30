package com.riders.thelabdesk.core.domain.utils

interface UseCase<T> {
    suspend operator fun invoke(params: Any): Resource <T?>
}