package core.compose.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.WindowState
import core.compose.utils.WindowDraggableArea
import log.Timber
import java.awt.Toolkit


val toolbarDefaultColor = Color(75, 75, 75)
val toolbarFocusedColor = Color(80, 80, 80)

@Preview
@Composable
fun Logo() {
    Icon(
        modifier = Modifier.padding(start = 20.dp),
        imageVector = Icons.Filled.LabelImportant, contentDescription = "logo_icon"
    )
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Preview
@Composable
fun SearchBar(modifier: Modifier) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier.fillMaxHeight().padding(bottom = 4.dp),
        value = text,
        onValueChange = { newValue -> text = newValue },
        label = {
            Text("Search")
        },
        placeholder = {
            Text(text = "Search")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = Color(0xFF121212)
            )
        },
        trailingIcon = {
            if (text.isNotBlank()) {
                IconButton(
                    onClick = { text = "" }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "close_icon",
                        tint = Color(0xFF121212)
                    )
                }
            }
        },
        textStyle = TextStyle(textAlign = TextAlign.Start, fontSize = 14.sp, color = Color.White),
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences, autoCorrect = false, keyboardType = KeyboardType.Text
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = toolbarFocusedColor,
            unfocusedContainerColor = toolbarDefaultColor,
            focusedBorderColor = Color.LightGray,
            unfocusedBorderColor = Color(0xFF333333),
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color(0xFF121212)
        ),
    )
}

@Preview
@Composable
fun WindowActions(
    windowScope: WindowScope,
    state: WindowState,
    onMinimize: () -> Unit = {
        Timber.d("Click on Minimize icon")
        state.isMinimized = state.isMinimized.not()
    },
    onClose: () -> Unit
) {
    val mode = remember { mutableStateOf(state.placement) }
    val size = remember { mutableStateOf(windowScope.window.size) }
    val position = remember { mutableStateOf(state.position) }

    Row(
        modifier = Modifier.fillMaxHeight(),
        horizontalArrangement = Arrangement.spacedBy(space = 4.dp, alignment = Alignment.End),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(30.dp).clickable(onClick = onMinimize),
            imageVector = Icons.Filled.Minimize,
            contentDescription = "minimize"
        )
        Icon(
            modifier = Modifier.size(30.dp).clickable {
                Timber.d("Click on Maximize icon")
                mode.value = if (mode.value == WindowPlacement.Floating) {
                    size.value = windowScope.window.size
                    position.value = state.position
                    val insets = Toolkit.getDefaultToolkit().getScreenInsets(windowScope.window.graphicsConfiguration)
                    val bounds = windowScope.window.graphicsConfiguration.bounds
                    windowScope.window.setSize(bounds.width, bounds.height - insets.bottom)
                    windowScope.window.setLocation(0, 0)
                    WindowPlacement.Maximized
                } else {
                    windowScope.window.size = size.value
                    state.position = position.value
                    WindowPlacement.Floating
                }
            },
            imageVector = if (state.placement != WindowPlacement.Fullscreen) Icons.Filled.Fullscreen else Icons.Filled.FullscreenExit,
            contentDescription = "maximize"
        )
        Icon(
            modifier = Modifier.size(30.dp).clickable(onClick = onClose),
            imageVector = Icons.Filled.Close,
            contentDescription = "close_icon"
        )
    }
}

@Preview
@Composable
fun WindowScope.AppTitleBar(
    windowState: WindowState,
    onClose: () -> Unit
) {
    WindowDraggableArea {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(color = toolbarDefaultColor),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Logo()
            SearchBar(modifier = Modifier.weight(1.25f))
            WindowActions(
                windowScope = this@AppTitleBar,
                state = windowState,
                onClose = onClose
            )
        }
    }
}