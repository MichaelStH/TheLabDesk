package core.compose.component

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CursorDropdownMenu
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerButton
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.WindowState
import core.compose.theme.TheLabDeskTheme
import core.compose.theme.md_theme_dark_primaryContainer
import core.compose.utils.Text
import core.compose.utils.WindowDraggableArea
import core.log.Timber
import di.AppModule
import ui.main.MainViewModel
import java.awt.Toolkit


val toolbarDefaultColor = Color(75, 75, 75)
val toolbarFocusedColor = Color(80, 80, 80)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LogoAndMenu(viewModel: MainViewModel, modifier: Modifier) {
    Box(modifier = modifier) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier.size(56.dp).padding(start = 20.dp),
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
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SearchBar(viewModel: MainViewModel, modifier: Modifier) {
    // Declaring Coroutine scope
    val scope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isFocus by interactionSource.collectIsFocusedAsState()
    val focusManager: FocusManager = LocalFocusManager.current
    val density = LocalDensity.current

    BoxWithConstraints(
        modifier = Modifier.then(modifier).fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = !viewModel.isDynamicIslandVisible,
            enter = slideInVertically {
                // Slide in from 40 dp from the top.
                with(density) { -40.dp.roundToPx() }
            } + fadeIn(
                // Fade in with the initial alpha of 0.3f.
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically {
                // Slide in from 40 dp from the top.
                with(density) { -40.dp.roundToPx() }
            } + fadeOut()
        ) {
            Card(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(Alignment.Center)
                    .animateContentSize(animationSpec = spring(stiffness = Spring.StiffnessMediumLow)),
                onClick = {
                    Timber.d("Is pressed!")
                    viewModel.updateIsDynamicIslandVisible(!viewModel.isDynamicIslandVisible)
                    viewModel.displayDynamicIsland(viewModel.isDynamicIslandVisible)
                },
                shape = CircleShape
            ) {

                Row(
                    modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier,
                        imageVector = Icons.Filled.Search,
                        contentDescription = "search icon"
                    )

                    TheLabDeskText(
                        modifier = Modifier.padding(end = 8.dp),
                        text = "Search an App...",
                        style = TextStyle(color = Color.White, textAlign = TextAlign.Center)
                    )
                }
            }
        }

        // Dynamic Island
        AnimatedVisibility(
            modifier = Modifier.fillMaxSize().padding(top = 20.dp),
            visible = if (LocalInspectionMode.current) true else viewModel.isDynamicIslandVisible,

            enter = slideInVertically {
                // Slide in from 40 dp from the top.
                with(density) { -40.dp.roundToPx() }
            } + fadeIn(
                // Fade in with the initial alpha of 0.3f.
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically {
                // Slide in from 40 dp from the top.
                with(density) { -40.dp.roundToPx() }
            } + fadeOut()
        ) {
            DynamicIsland(
                viewModel = viewModel,
                modifier = Modifier.fillMaxSize().clip(shape = RoundedCornerShape(22.dp)),
                islandUiState = viewModel.dynamicIslandState.value
            )
        }
    }
}


@Preview
@Composable
fun WindowActions(
    modifier: Modifier,
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

    Box(modifier = modifier) {

        Row(
            modifier = Modifier.fillMaxHeight().align(Alignment.TopEnd),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top
        ) {
            Box(modifier = Modifier.size(36.dp).clickable(onClick = onMinimize), contentAlignment = Alignment.Center) {
                Icon(
                    modifier = Modifier.fillMaxSize().padding(8.dp),
                    imageVector = Icons.Filled.Minimize,
                    contentDescription = "minimize",
                    tint = Color.LightGray
                )
            }

            Box(
                modifier = Modifier.size(36.dp).clickable {
                    Timber.d("Click on Maximize icon")
                    mode.value = if (mode.value == WindowPlacement.Floating) {
                        size.value = windowScope.window.size
                        position.value = state.position
                        val insets =
                            Toolkit.getDefaultToolkit().getScreenInsets(windowScope.window.graphicsConfiguration)
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
                    .size(36.dp)
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
}

@Composable
fun WindowScope.AppTitleBar(
    viewModel: MainViewModel,
    windowState: WindowState,
    onClose: () -> Unit
) {
    WindowDraggableArea {
        Row(
            modifier = Modifier.fillMaxWidth().height(68.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LogoAndMenu(viewModel = viewModel, modifier = Modifier.weight(.7f))
            SearchBar(viewModel = viewModel, modifier = Modifier.weight(2f))
            WindowActions(
                modifier = Modifier.weight(.7f),
                windowScope = this@AppTitleBar,
                state = windowState,
                onClose = onClose
            )
        }
    }
}

/////////////////////////////////////////////////
//
// PREVIEWS
//
/////////////////////////////////////////////////
@Preview
@Composable
private fun PreviewAppTitleBar() {
    val viewModel = MainViewModel(AppModule.injectDependencies())
    viewModel.updateDarkMode(true)

    TheLabDeskTheme {
        TheLabDeskSurface(modifier = Modifier) {
            Column {
                Box(
                    modifier = Modifier.fillMaxWidth().heightIn(0.dp, 100.dp),
                    contentAlignment = Alignment.CenterStart
                ) { LogoAndMenu(viewModel = viewModel, modifier = Modifier.fillMaxWidth()) }

                Box(
                    modifier = Modifier.fillMaxWidth().heightIn(0.dp, 100.dp),
                    contentAlignment = Alignment.CenterStart
                ) { SearchBar(viewModel = viewModel, modifier = Modifier.fillMaxWidth()) }
            }
        }
    }
}