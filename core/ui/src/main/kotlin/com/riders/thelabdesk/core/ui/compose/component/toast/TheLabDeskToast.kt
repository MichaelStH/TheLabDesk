package com.riders.thelabdesk.core.ui.compose.component.toast

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.riders.thelabdesk.core.common.log.Timber
import com.riders.thelabdesk.core.ui.compose.component.TheLabDeskCard
import com.riders.thelabdesk.core.ui.compose.theme.TheLabDeskTheme
import com.riders.thelabdesk.core.ui.compose.theme.md_theme_dark_primaryContainer
import com.riders.thelabdesk.core.ui.compose.theme.md_theme_light_primaryContainer
import com.riders.thelabdesk.core.ui.compose.utils.Text
import com.riders.thelabdesk.core.ui.utils.ToastManager
import kotlinx.coroutines.delay

enum class Toast(val delayTime: Long) {
    LENGTH_SHORT(3_000L), LENGTH_LONG(5_000L)
}

@Composable
fun Toast(
    modifier: Modifier = Modifier,
    message: String,
    toastDelayTime: Toast = Toast.LENGTH_SHORT,
    color: Color = Color.Transparent
) {
    Timber.d("Toast() | message: $message")

    var animateContent by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth(.7f)
            .height(150.dp)
            .padding(bottom = 72.dp)
            .background(Color.Transparent)
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            modifier = Modifier.wrapContentSize(),
            visible = animateContent,
            enter = fadeIn(tween(durationMillis = 300)) + slideInVertically { 150 },
            exit = slideOutVertically { 150 } + fadeOut(tween(durationMillis = 300))
        ) {
            TheLabDeskCard(modifier = Modifier, color = color) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = message,
                        color = if (!isSystemInDarkTheme()) Color.White else Color.Black
                    )
                }
            }/*
            Row(
                modifier = Modifier.padding().background(color).clip(RoundedCornerShape(12.dp)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message,
                    color = if (!isSystemInDarkTheme()) Color.White else Color.Black
                )
            }*/
        }
    }

    LaunchedEffect(Unit) {
        animateContent = true
        delay(toastDelayTime.delayTime)
        animateContent = false

        delay(5000L)
        ToastManager.hide()
    }
}

@Preview
@Composable
private fun PreviewToast() {
    TheLabDeskTheme(true) {
        Column {

            Box(Modifier.height(150.dp)) {
                Toast(
                    message = "Preview Toast",
                    color = if (!isSystemInDarkTheme()) md_theme_dark_primaryContainer else md_theme_light_primaryContainer
                )
            }
        }
    }
}