package data.local.model.compose

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@Stable
sealed class IslandUiState(
    val contentSize: DpSize = DpSize(150.dp, 50.dp),
    val hasMainContent: Boolean = false,

    val leadingContentSize: Dp = Dp.Hairline,
    val hasLeadingContent: Boolean = false,

    val trailingContentSize: Dp = Dp.Hairline,
    val hasTrailingContent: Boolean = false,

    val bubbleContentSize: DpSize = DpSize(50.dp, 50.dp),
    val hasBubbleContent: Boolean = false
) {
    val fullWidth = contentSize.width + leadingContentSize + trailingContentSize

    @Stable
    class DefaultState : IslandUiState()

    @Stable
    class WelcomeState : IslandUiState(
        contentSize = DpSize(
            300.dp, 50.dp
        ),
    )

    @Stable
    class SearchState : IslandUiState(
        contentSize = DpSize(
            350.dp, 70.dp
        ),
    )

    @Stable
    class FaceUnlockState : IslandUiState(
        contentSize = DpSize(
            150.dp, 150.dp
        ),
        hasMainContent = true,
    )

    @Stable
    class CallState : IslandUiState(
        leadingContentSize = 65.dp,
        trailingContentSize = 55.dp,
        hasLeadingContent = true,
        hasTrailingContent = true,
    )

    @Stable
    class CallTimerState : IslandUiState(
        leadingContentSize = 50.dp,
        hasLeadingContent = true,
        hasBubbleContent = true
    )
}
