package core.compose.component

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.RichTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalViewConfiguration
import kotlinx.coroutines.launch

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

    val tooltipState = remember { RichTooltipState() }
    val scope = rememberCoroutineScope()

    RichTooltipBox(
        modifier = modifier,
        title = { Text("Add others") },
        action = {
            TextButton(
                onClick = { scope.launch { tooltipState.dismiss() } }
            ) { Text("Learn More") }
        },
        text = { Text("Share this collection with friends...") },
        tooltipState = tooltipState
    ) {
        IconButton(
            onClick = { /* Icon button's click event */ },
            modifier = Modifier.tooltipAnchor()
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
