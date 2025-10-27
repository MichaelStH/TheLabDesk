package data.local.model.compose

import androidx.compose.runtime.Stable

@Stable
data class Progress(
    val fraction: Float,
    // TODO: Use kotlin.time.Duration when Kotlin version is updated.
    //  See https://github.com/Kotlin/api-guidelines/issues/6
    val timeMillis: Long
)