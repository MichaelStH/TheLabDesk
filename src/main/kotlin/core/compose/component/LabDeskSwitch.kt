package core.compose.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.compose.theme.TheLabDeskTheme
import core.compose.utils.Text
import core.compose.utils.animateAlignmentAsState
import di.AppModule
import viewmodel.MainViewModel

@Composable
fun TheLabDeskSwitch(
    viewModel: MainViewModel,
    width: Dp = 64.dp,
    height: Dp = 32.dp,
    checkedTrackColor: Color = Color(0xFF35898F),
    uncheckedTrackColor: Color = Color(0xFF000000),
    gapBetweenThumbAndTrackEdge: Dp = 8.dp,
    borderWidth: Dp = 4.dp,
    cornerSize: Int = 50,
    iconInnerPadding: Dp = 4.dp,
    thumbSize: Dp = 16.dp
) {

    // this is to disable the ripple effect
    val interactionSource = remember {
        MutableInteractionSource()
    }

    // state of the switch
//    var switchOn by remember { mutableStateOf(true) }

    // for moving the thumb
    val alignment by animateAlignmentAsState(if (viewModel.isDarkMode) 1f else -1f)

    // outer rectangle with border
    Box(
        modifier = Modifier
            .size(width = width, height = height)
            .border(
                width = borderWidth,
                color = if (viewModel.isDarkMode) checkedTrackColor else uncheckedTrackColor,
                shape = RoundedCornerShape(percent = cornerSize)
            )
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
//                switchState = !switchState
                viewModel.updateDarkMode(!viewModel.isDarkMode)
            },
        contentAlignment = Alignment.Center
    ) {

        // this is to add padding at the each horizontal side
        Box(
            modifier = Modifier
                .padding(
                    start = gapBetweenThumbAndTrackEdge,
                    end = gapBetweenThumbAndTrackEdge
                )
                .fillMaxSize(),
            contentAlignment = alignment
        ) {

            // thumb with icon
            Icon(
                modifier = Modifier
                    .size(size = thumbSize)
                    .background(
                        color = if (viewModel.isDarkMode) checkedTrackColor else uncheckedTrackColor,
                        shape = CircleShape
                    )
                    .padding(all = iconInnerPadding),
                imageVector = if (viewModel.isDarkMode) Icons.Filled.Done else Icons.Filled.Close,
                contentDescription = if (viewModel.isDarkMode) "Enabled" else "Disabled",
                tint = Color.White
            )
        }
    }

    // gap between switch and the text
    // Spacer(modifier = Modifier.height(height = 16.dp))
    Box(modifier = Modifier.size(width), contentAlignment = Alignment.Center) {
        Text(
            modifier = Modifier.matchParentSize(),
            text = if (viewModel.isDarkMode) "ON" else "OFF",
            color = Color.Black
        )
    }
}

@Preview
@Composable
private fun PreviewTheLabDeskSwitch() {
    val viewModel: MainViewModel = MainViewModel(AppModule.injectDependencies())
    viewModel.updateDarkMode(true)
    TheLabDeskTheme {
        Column {
            TheLabDeskSwitch(viewModel)
        }
    }
}