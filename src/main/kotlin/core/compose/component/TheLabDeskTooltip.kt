package core.compose.component

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TooltipState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier

enum class TooltipAlignment {
    BottomCenter,
    TopCenter,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TheLabDeskTooltipPopup(
    modifier: Modifier = Modifier,
    requesterView: @Composable (Modifier) -> Unit,
    tooltipContent: @Composable () -> Unit,
) {
    val tooltipState: TooltipState = rememberTooltipState()
    val scope = rememberCoroutineScope()

    TooltipBox(
        modifier = modifier,
        state = tooltipState,
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            Text("Add others")
        },
        enableUserInput = true,
        /*title = { },
        action = {
            TextButton(
                onClick = { scope.launch { tooltipState.dismiss() } }
            ) { Text("Learn More") }
        },
        text = { Text("Share this collection with friends...") },*/
    ) {
        IconButton(
            onClick = { /* Icon button's click event */ }
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "About icon"
            )
        }
    }
}

/*
fun calculateTooltipPopupPosition(
    view: View,
    coordinates: LayoutCoordinates?,
): TooltipPopupPosition {
    coordinates ?: return TooltipPopupPosition()

    val visibleWindowBounds = android.graphics.Rect()
    view.getWindowVisibleDisplayFrame(visibleWindowBounds)

    val boundsInWindow = coordinates.boundsInWindow()

    val heightAbove = boundsInWindow.top - visibleWindowBounds.top
    val heightBelow = visibleWindowBounds.bottom - visibleWindowBounds.top - boundsInWindow.bottom

    val centerPositionX = boundsInWindow.right - (boundsInWindow.right - boundsInWindow.left) / 2

    val offsetX = centerPositionX - visibleWindowBounds.centerX()

    return if (heightAbove < heightBelow) {
        val offset = IntOffset(
            y = coordinates.size.height,
            x = offsetX.toInt()
        )
        TooltipPopupPosition(
            offset = offset,
            alignment = TooltipAlignment.TopCenter,
            centerPositionX = centerPositionX,
        )
    } else {
        TooltipPopupPosition(
            offset = IntOffset(
                y = -coordinates.size.height,
                x = offsetX.toInt()
            ),
            alignment = TooltipAlignment.BottomCenter,
            centerPositionX = centerPositionX,
        )
    }
}*/
