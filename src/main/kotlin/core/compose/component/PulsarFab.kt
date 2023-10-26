package core.compose.component

import androidx.compose.animation.core.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import core.compose.theme.TheLabDeskTheme


@Composable
fun PulsarFab(content: @Composable () -> Unit) {
    MultiplePulsarEffect { modifier ->
        FloatingActionButton(
            modifier = modifier,
            shape = FloatingActionButtonDefaults.largeShape,
            containerColor = MaterialTheme.colorScheme.primary,
            onClick = { },
        ) { content() }
    }
}

@Composable
fun PulsarFabWithClick(onClick: () -> Unit) {
    MultiplePulsarEffect { modifier ->
        FloatingActionButton(
            modifier = modifier,
            shape = FloatingActionButtonDefaults.largeShape,
            containerColor = MaterialTheme.colorScheme.primary,
            onClick = onClick,
        ) { Icon(imageVector = Icons.Default.Send, contentDescription = "") }
    }
}

@Composable
fun MultiplePulsarEffect(
    nbPulsar: Int = 2,
    pulsarRadius: Float = 25f,
    pulsarColor: Color = MaterialTheme.colorScheme.primary,
    fab: @Composable (Modifier) -> Unit = {}
) {
    var fabSize by remember { mutableStateOf(IntSize(0, 0)) }

    val effects: List<Pair<Float, Float>> = List(nbPulsar) {
        pulsarBuilder(pulsarRadius = pulsarRadius, size = fabSize.width, delay = it * 500)
    }

    Box(
        Modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            Modifier,
            onDraw = {
                for (i in 0 until nbPulsar) {
                    val (radius, alpha) = effects[i]
                    drawCircle(color = pulsarColor, radius = radius, alpha = alpha)
                }
            })
        fab(
            Modifier
                .padding((pulsarRadius * 2).dp)
                .onGloballyPositioned {
                    if (it.isAttached) {
                        fabSize = it.size
                    }
                }
        )
    }
}

@Composable
fun pulsarBuilder(pulsarRadius: Float, size: Int, delay: Int): Pair<Float, Float> {
    val infiniteTransition = rememberInfiniteTransition(label = "pulsar transition")

    val radius by infiniteTransition.animateFloat(
        initialValue = (size / 2).toFloat(),
        targetValue = size + (pulsarRadius * 2),
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(3000),
            initialStartOffset = StartOffset(delay),
            repeatMode = RepeatMode.Restart
        ), label = "radius transition"
    )
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(3000),
            initialStartOffset = StartOffset(delay + 100),
            repeatMode = RepeatMode.Restart
        ), label = "alpha transition"
    )

    return radius to alpha
}


@Preview
@Composable
private fun PreviewPulsarFab() {
    TheLabDeskTheme {
        PulsarFab {
        }
    }
}