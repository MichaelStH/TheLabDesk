package core.compose.component

import androidx.compose.animation.core.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.compose.theme.TheLabDeskTheme
import core.compose.theme.md_theme_dark_background
import core.compose.theme.samsungSangFamily

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
            modifier = Modifier.clipToBounds().fillMaxWidth().padding(1.dp).drawWithContent {
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
    val progress by remember { mutableFloatStateOf(0f) }

    TheLabDeskTheme {
        CardWithAnimatedBorder() {
            Column(
                modifier = Modifier.fillMaxWidth(.8f).fillMaxHeight(.8f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
            ) {
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
}