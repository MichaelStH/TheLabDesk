package core.compose.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.compose.theme.TheLabDeskTheme
import core.compose.theme.md_theme_dark_primary
import core.log.Timber
import data.local.model.compose.IslandUiState
import di.AppModule
import ui.main.MainViewModel

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Search(viewModel: MainViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val focusRequester = remember { FocusRequester() }

    Box(modifier = Modifier.fillMaxSize().padding(0.dp)) {
        TextField(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .focusRequester(focusRequester)
                .onFocusChanged {
                    if (viewModel.isSearchFocused != it.isFocused) {
                        viewModel.updateIsSearchFocused(it.isFocused)

                        if (!it.isFocused) {
                            viewModel.updateIsSearchFocused(false)
                            viewModel.updateKeyboardVisible(it.isFocused)
                        } else {
                            viewModel.updateIsSearchFocused(true)
                            viewModel.updateKeyboardVisible(it.isFocused)
                        }
                    }
                },
            value = if (LocalInspectionMode.current) "Value Lorem" else viewModel.searchedAppRequest,
            onValueChange = { viewModel.searchApp(it) },
            placeholder = { Text(text = "Search an App...", style = TextStyle(fontSize = 12.sp)) },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedPlaceholderColor = Color.LightGray,
                disabledTextColor = Color.Transparent,
                cursorColor = md_theme_dark_primary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(fontSize = 12.sp, textAlign = TextAlign.Start),
            singleLine = true,
            maxLines = 1,
            trailingIcon = {
                if (viewModel.searchedAppRequest.isNotBlank()) {
                    IconButton(
                        onClick = { viewModel.searchApp("") }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "close_icon"
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                KeyboardType.Text,
                ImeAction.Done
            )
        )
    }

    if (viewModel.dynamicIslandState.value is IslandUiState.SearchState) {
        Timber.d("Should request focus for textField")
    }
}


@Preview
@Composable
private fun PreviewSearch() {
    val viewModel = MainViewModel(AppModule.injectDependencies())
    viewModel.updateDarkMode(true)

    TheLabDeskTheme {
        TheLabDeskSurface(modifier = Modifier) {
            Search(viewModel)
        }
    }
}