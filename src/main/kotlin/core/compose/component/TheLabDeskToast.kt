package core.compose.component

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.compose.theme.TheLabDeskTheme
import core.compose.theme.isSystemInDarkTheme
import core.compose.theme.md_theme_dark_primaryContainer
import core.compose.theme.md_theme_light_primaryContainer
import core.compose.utils.Text
import core.log.Timber
import di.AppModule
import kotlinx.coroutines.delay
import ui.main.MainViewModel

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
            .height(100.dp)
            .padding(bottom = 72.dp)
            .background(Color.Transparent)
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = animateContent,
            enter = fadeIn(tween(durationMillis = 300)) + slideInVertically { 150 },
            exit = slideOutVertically { 150 } + fadeOut(tween(durationMillis = 300))
        ) {
            Row(
                modifier = Modifier.background(color).clip(RoundedCornerShape(12.dp)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message,
                    color = if (!isSystemInDarkTheme()) Color.White else Color.Black
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        animateContent = true
        delay(toastDelayTime.delayTime)
        animateContent = false

        delay(5000L)
        ToastViewModel.hide()
    }
}

@Preview
@Composable
private fun PreviewToast() {
    val viewModel: MainViewModel = MainViewModel(AppModule.injectDependencies())
    viewModel.updateDarkMode(false)

    TheLabDeskTheme {
        Column {

            Box(Modifier.height(150.dp)) {
                Toast(
                    message = "Preview Toast",
                    color = if (!isSystemInDarkTheme()) md_theme_dark_primaryContainer else md_theme_light_primaryContainer
                )
            }

            viewModel.updateDarkMode(true)
            Box(Modifier.height(150.dp)) {
                Toast(
                    message = "Preview Toast",
                    color = if (!isSystemInDarkTheme()) md_theme_dark_primaryContainer else md_theme_light_primaryContainer
                )
            }
        }
    }
}