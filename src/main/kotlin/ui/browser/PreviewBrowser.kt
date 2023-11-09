package ui.browser

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.compose.component.TheLabDeskSurface
import core.compose.component.browser.Browser
import core.compose.theme.TheLabDeskTheme
import core.compose.theme.md_theme_dark_primary


//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@Composable
fun SearchWebView(viewModel: BrowserViewModel) {

    val focusRequester = remember { FocusRequester() }

    Box(modifier = Modifier.fillMaxWidth().padding(0.dp)) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .focusRequester(focusRequester)
                .onFocusChanged {
                    if (viewModel.isSearchFocused != it.isFocused) {
                        viewModel.updateIsSearchFocused(it.isFocused)

                        if (!it.isFocused) {
                            viewModel.updateIsSearchFocused(false)
                        } else {
                            viewModel.updateIsSearchFocused(true)
                        }
                    }
                },
            value = if (LocalInspectionMode.current) "Value Lorem" else viewModel.currentUrl,
            onValueChange = { viewModel.updateCurrentUrl(it) },
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
            /*trailingIcon = {
                if (viewModel.currentUrl.isNotBlank()) {
                    IconButton(
                        onClick = { viewModel.updateCurrentUrl(Constants.URL_GOOGLE) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "close_icon"
                        )
                    }
                }
            },*/
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                KeyboardType.Text,
                ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { viewModel.search() })
        )
    }
}

@Preview
@Composable
fun BrowserContent(composeWindow: ComposeWindow, viewModel: BrowserViewModel) {
    TheLabDeskTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(50)) {
                SearchWebView(viewModel)
            }

            Browser(
                composeWindow = composeWindow,
                modifier = Modifier.fillMaxSize(),
                viewModel = viewModel,
                url = viewModel.currentUrl
            )
        }
    }
}


//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@Preview
@Composable
private fun PreviewSearchWebView() {
    val viewModel = BrowserViewModel()
    viewModel.updateDarkMode(true)

    TheLabDeskTheme {
        TheLabDeskSurface(modifier = Modifier) {
            SearchWebView(viewModel)
        }
    }
}