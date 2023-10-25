package ui.splashscreen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.compose.component.CardWithAnimatedBorder
import core.compose.component.TheLabDeskLogo
import core.compose.theme.TheLabDeskTheme
import core.compose.theme.samsungSangFamily
import di.AppModule
import kotlinx.coroutines.delay
import ui.main.MainViewModel

//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@Composable
fun SplashScreen(viewModel: MainViewModel) {
    var progress by remember { mutableFloatStateOf(0f) }

    TheLabDeskTheme {
        CardWithAnimatedBorder(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            Column(
                modifier = Modifier.fillMaxWidth(.8f).fillMaxHeight(.8f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
            ) {
                TheLabDeskLogo(modifier = Modifier.size(120.dp))
                Text(
                    text = "Loading...", style = TextStyle(
                        fontFamily = samsungSangFamily,
                        fontWeight = FontWeight.W600,
                        fontSize = 18.sp,
                        letterSpacing = 1.4.sp,
                        textAlign = TextAlign.Start,
                        color = Color.LightGray
                    )
                )
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(.75f),
                    progress = if (LocalInspectionMode.current) .45f else progress
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        while (progress < 1f) {
            progress += .01f
            delay(25)
        }

        viewModel.updateIsLoading(true)
    }
}


//////////////////////////////////////////////////
//
// PREVIEWS
//
//////////////////////////////////////////////////
@Preview
@Composable
private fun PreviewSplashScreen() {
    val viewModel: MainViewModel = MainViewModel(AppModule.injectDependencies())
    TheLabDeskTheme {
        SplashScreen(viewModel)
    }
}