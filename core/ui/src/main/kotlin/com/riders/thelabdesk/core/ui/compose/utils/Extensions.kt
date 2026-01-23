package com.riders.thelabdesk.core.ui.compose.utils

import androidx.compose.material3.ColorScheme


fun ColorScheme.getColorScheme(): ColorScheme = this
    .apply {
        // Timber.d("Extensions | getColorScheme()")
    }
    .run {
        ColorScheme(
            primary,
            onPrimary,
            primaryContainer,
            onPrimaryContainer,
            inversePrimary,
            secondary,
            onSecondary,
            secondaryContainer,
            onSecondaryContainer,
            tertiary,
            onTertiary,
            tertiaryContainer,
            onTertiaryContainer,
            background,
            onBackground,
            surface,
            onSurface,
            surfaceVariant,
            onSurfaceVariant,
            surfaceTint,
            inverseSurface,
            inverseOnSurface,
            error,
            onError,
            errorContainer,
            onErrorContainer,
            outline,
            outlineVariant,
            scrim
        )
    }