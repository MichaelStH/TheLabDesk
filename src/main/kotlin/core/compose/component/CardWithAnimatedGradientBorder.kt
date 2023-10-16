package core.compose.component

import androidx.compose.animation.core.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import core.compose.theme.TheLabDeskTheme
import core.compose.theme.md_theme_dark_background

val colorBg = Color(0xFF2C3141)
val colors =
    listOf(
        Color(0xFFFF595A),
        Color(0xFFFFC766),
        Color(0xFF35A07F),
        Color(0xFF35A07F),
        Color(0xFFFFC766),
        Color(0xFFFF595A)
    )


@Composable
fun CardWithAnimatedBorder(
    modifier: Modifier = Modifier,
    // onCardClick: () -> Unit = {},
    borderColors: List<Color> = colors,
    // borderColors: List<Color> = emptyList(),
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec =
        infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val brush =
        if (borderColors.isNotEmpty()) Brush.sweepGradient(borderColors)
        else Brush.sweepGradient(listOf(Color.Gray, Color.White))

    Surface(modifier = modifier, shape = RoundedCornerShape(20.dp)) {
        Surface(
            modifier =
            Modifier.clipToBounds().fillMaxWidth().padding(1.dp).drawWithContent {
                rotate(angle) {
                    drawCircle(
                        brush = brush,
                        radius = size.width,
                        blendMode = BlendMode.SrcIn,
                    )
                }
                drawContent()
            },
            color = md_theme_dark_background,
            shape = RoundedCornerShape(19.dp)
        ) {
            Box(modifier = Modifier.padding(8.dp), contentAlignment = Alignment.Center) { content() }
        }
    }
}


@Preview
@Composable
private fun PreviewCardWithAnimatedBorder() {
    TheLabDeskTheme {
        CardWithAnimatedBorder() { }
    }
}