package core.compose.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import core.compose.theme.Orange
import core.compose.theme.TheLabDeskTheme
import di.AppModule
import ui.main.MainViewModel
import kotlin.random.Random


@Composable
fun Waveform(color: Color, limit: Float = 1f) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        for (i in 0..4) {
            val height = remember { Animatable(0f) }

            LaunchedEffect(Unit) {
                while (true) {
                    height.animateTo(
                        Random.nextFloat() * limit,
                        animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .background(
                        color = color,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .fillMaxHeight(height.value)
                    .weight(1f)
            )
            Box(modifier = Modifier.width(2.dp))
        }
    }
}


@Composable
fun CallWaveform() {
    Box(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize()
    ) {
        Waveform(color = Green)
        Waveform(color = Orange)
        Waveform(color = White, limit = .7f)

    }
}

@Preview
@Composable
private fun PreviewCallWaveform() {
    val viewModel = MainViewModel(AppModule.injectDependencies())
    viewModel.updateDarkMode(true)

    TheLabDeskTheme(viewModel.isDarkMode) {
        TheLabDeskSurface(modifier = Modifier) {
            CallWaveform()
        }
    }
}