package com.riders.thelabdesk.core.ui.compose.component.dynamicisland

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.riders.thelabdesk.core.ui.compose.component.TheLabDeskSurface
import com.riders.thelabdesk.core.ui.compose.component.TheLabDeskText
import com.riders.thelabdesk.core.ui.compose.theme.TheLabDeskTheme

@Preview
@Composable
fun Welcome() {
    Row(
        modifier = Modifier.padding(16.dp).fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TheLabDeskText(modifier = Modifier, text = "Welcome to ")

        Image(
            modifier = Modifier.height(16.dp),
            painter = painterResource(resourcePath = "images/ic_lab_6_the.xml"),
            contentDescription = "the_icon",
            colorFilter = ColorFilter.tint(if (!isSystemInDarkTheme()) Color.Black else Color.White)
        )

        Image(
            modifier = Modifier.height(16.dp),
            painter = painterResource(resourcePath = "images/ic_lab_6_lab.xml"),
            contentDescription = "lab_icon",
            colorFilter = ColorFilter.tint(if (!isSystemInDarkTheme()) Color.Black else Color.White)
        )
    }
}

@Preview
@Composable
private fun PreviewWelcome() {
    TheLabDeskTheme(true) {
        TheLabDeskSurface(modifier = Modifier.fillMaxWidth().heightIn(0.dp, 500.dp)) {
            Welcome()
        }
    }
}
