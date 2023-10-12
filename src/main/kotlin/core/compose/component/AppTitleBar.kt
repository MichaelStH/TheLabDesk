package core.compose.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CursorDropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerButton
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.WindowState
import core.compose.theme.md_theme_dark_primaryContainer
import core.compose.utils.Text
import core.compose.utils.WindowDraggableArea
import core.log.Timber
import viewmodel.MainViewModel
import java.awt.Toolkit


val toolbarDefaultColor = Color(75, 75, 75)
val toolbarFocusedColor = Color(80, 80, 80)

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun LogoAndMenu(viewModel: MainViewModel) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            modifier = Modifier.size(64.dp).padding(start = 20.dp),
            painter = painterResource(resourcePath = "images/ic_lab.png"),
            contentDescription = "logo_icon"
        )

        repeat(viewModel.menuOptions.size) { index ->
            var expanded by remember { mutableStateOf(false) }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .onClick(
                        matcher = PointerMatcher.mouse(PointerButton.Primary), // add onClick for every required PointerButton
                        keyboardModifiers = { true }, // e.g { isCtrlPressed }; Remove it to ignore keyboardModifiers
                        onClick = {
                            expanded = true

                        }
                    )
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = viewModel.menuOptions.elementAt(index).first,
                    style = TextStyle(fontSize = 18.sp, color = Color.LightGray)
                )
            }

            // tmp fix for https://github.com/JetBrains/compose-jb/issues/2012
            var renderCount by remember { mutableStateOf(0) }
            listOf(renderCount, renderCount - 1).forEach { renderId ->
                val isActive = renderId == renderCount
                key(renderId) {
                    CursorDropdownMenu(
                        expanded = expanded && isActive,
                        onDismissRequest = {
                            if (isActive) {
                                renderCount += 1
                                expanded = false
                            }
                        },
                    ) {
                        repeat(viewModel.menuOptions.elementAt(index).second.size) {
                            DropdownMenuItem(
                                text = { Text(viewModel.menuOptions.elementAt(index).second.elementAt(it).first) },
                                onClick = {
                                    viewModel.menuOptions.elementAt(index).second.elementAt(it).second.invoke()
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
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
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val mode = remember { mutableStateOf(state.placement) }
    val size = remember { mutableStateOf(windowScope.window.size) }
    val position = remember { mutableStateOf(state.position) }

    Row(
        modifier = Modifier.fillMaxHeight(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        Box(modifier = Modifier.size(40.dp).clickable(onClick = onMinimize), contentAlignment = Alignment.Center) {
            Icon(
                modifier = Modifier.fillMaxSize().padding(8.dp),
                imageVector = Icons.Filled.Minimize,
                contentDescription = "minimize",
                tint = Color.LightGray
            )
        }

        Box(
            modifier = Modifier.size(40.dp).clickable {
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
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.fillMaxSize().padding(8.dp),
                imageVector = if (state.placement != WindowPlacement.Fullscreen) Icons.Filled.Fullscreen else Icons.Filled.FullscreenExit,
                contentDescription = "maximize",
                tint = Color.LightGray
            )
        }

        Box(
            modifier = Modifier
                .size(40.dp)
                .clickable(onClick = onClose)
                .hoverable(interactionSource)
                .background(color = if (!isHovered) Color.Transparent else md_theme_dark_primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.fillMaxSize().padding(8.dp),
                imageVector = Icons.Filled.Close,
                contentDescription = "close_icon",
                tint = Color.LightGray
            )
        }
    }
}

@Preview
@Composable
fun WindowScope.AppTitleBar(
    viewModel: MainViewModel,
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
            LogoAndMenu(viewModel = viewModel)
            SearchBar(modifier = Modifier.weight(1.25f))
            WindowActions(
                windowScope = this@AppTitleBar,
                state = windowState,
                onClose = onClose
            )
        }
    }
}