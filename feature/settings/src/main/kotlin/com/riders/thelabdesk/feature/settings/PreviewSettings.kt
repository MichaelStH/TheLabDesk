package com.riders.thelabdesk.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.riders.thelabdesk.core.common.log.Timber
import com.riders.thelabdesk.core.ui.base.UiEvent
import com.riders.thelabdesk.core.ui.compose.component.TheLabDeskSwitch
import com.riders.thelabdesk.core.ui.compose.component.TheLabDeskText
import com.riders.thelabdesk.core.ui.compose.theme.TheLabDeskTheme


//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@Composable
fun SettingsContent(isDarkMode: Boolean, uiEvent: (UiEvent) -> Unit) {
    Timber.d("recomposition : SettingsContent()")

    val state = rememberLazyListState()

    TheLabDeskTheme {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            state = state
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TheLabDeskText(
                        modifier = Modifier,
                        text = if (isDarkMode) "Disable dark mode" else "Enable dark mode"
                    )

                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        TheLabDeskSwitch(isDarkMode, onCheckedChange = { uiEvent.invoke(UiEvent.OnUpdateDarkMode(it)) })
                    }
                }
            }
        }
    }
}

//////////////////////////////////////////////////
//
// PREVIEWS
//
//////////////////////////////////////////////////
@Preview
@Composable
private fun PreviewSettingsContent() {
    TheLabDeskTheme(true) {
        SettingsContent(isDarkMode = true) {}
    }
}
