package com.riders.thelabdesk.core.ui.compose.component.dynamicisland

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.riders.thelabdesk.core.ui.base.UiEvent
import com.riders.thelabdesk.core.ui.compose.component.TheLabDeskSurface
import com.riders.thelabdesk.core.ui.compose.theme.TheLabDeskTheme
import com.riders.thelabdesk.core.ui.compose.theme.md_theme_dark_primary

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Search(
    searchRequest: String,
    isSearchFocused: Boolean,
    uiEvent: (UiEvent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val focusRequester = remember { FocusRequester() }

    Box(modifier = Modifier.fillMaxSize().padding(0.dp)) {
        TextField(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .focusRequester(focusRequester)
                .onFocusChanged {
                    if (isSearchFocused != it.isFocused) {
                        uiEvent.invoke(UiEvent.OnUpdateIsSearchFocused(it.isFocused))

                        if (!it.isFocused) {
                            uiEvent.invoke(UiEvent.OnUpdateIsSearchFocused(false))
                            uiEvent.invoke(UiEvent.OnUpdateKeyboardVisible(it.isFocused))
                        } else {
                            uiEvent.invoke(UiEvent.OnUpdateIsSearchFocused(true))
                            uiEvent.invoke(UiEvent.OnUpdateKeyboardVisible(it.isFocused))
                        }
                    }
                },
            value = if (LocalInspectionMode.current) "Value Lorem" else searchRequest,
            onValueChange = { uiEvent.invoke(UiEvent.OnUpdateSearch(it)) },
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
                if (searchRequest.isNotBlank()) {
                    IconButton(
                        onClick = { uiEvent.invoke(UiEvent.OnUpdateSearch("")) }
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
}


@Preview
@Composable
private fun PreviewSearch() {
    TheLabDeskTheme {
        TheLabDeskSurface(modifier = Modifier) {
            Search("TheLa", true) {}
        }
    }
}