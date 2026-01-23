package com.riders.thelabdesk.core.ui.compose.component.browser

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import com.riders.thelabdesk.core.ui.compose.theme.TheLabDeskTheme
import javafx.embed.swing.JFXPanel

@Composable
fun WebView(
    composeWindow: ComposeWindow,
    jfxPanel: JFXPanel,
    modifier: Modifier = Modifier,
    url: String? = null,
    htmlContent: String? = null,
    onCreate: () -> Unit,
    onDestroy: () -> Unit = {}
) {
    require(null != url || null != htmlContent) {
        "url or htmlData is required"
    }

    TheLabDeskTheme {
        BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.Center) {
            ComposeJFXPanel(
                modifier = Modifier.matchParentSize(),
                composeWindow = composeWindow,
                jfxPanel = jfxPanel,
                onCreate = onCreate,
                onDestroy = onDestroy
            )
        }
    }
}