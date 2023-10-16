package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.compose.theme.TheLabDeskTheme
import core.compose.theme.samsungSangFamily
import core.compose.utils.Text


//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@Preview
@Composable
fun WelcomeContent() {
    TheLabDeskTheme {
        Card(
            modifier = Modifier.fillMaxWidth().heightIn(0.dp, 300.dp).padding(20.dp),
//                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
            shape = RoundedCornerShape(22.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {

                // Gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Black, Color.Black, Color.Black, Color.DarkGray, Color.Transparent
                                )
                            )
                        )
                        .align(Alignment.CenterStart)
                )

                // Background Image
                Column(
                    modifier = Modifier.fillMaxHeight().align(Alignment.CenterEnd).padding(end = 100.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
                ) {
                    Image(
                        modifier = Modifier,
                        painter = painterResource(resourcePath = "images/ic_lab_6_the.xml"),
                        contentDescription = "the_image",
                        colorFilter = ColorFilter.tint(Color.White, BlendMode.SrcIn)
                    )
                    Image(
                        modifier = Modifier,
                        painter = painterResource(resourcePath = "images/ic_lab_6_lab.xml"),
                        contentDescription = "lab_image",
                        colorFilter = ColorFilter.tint(Color.White, BlendMode.SrcIn)
                    )
                }

                // Gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Black, Color.Black, Color.Transparent
                                )
                            )
                        )
                        .align(Alignment.CenterStart)
                )

                // Welcome Text
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 50.dp)
                        .align(Alignment.CenterStart),
                    text = "Welcome to TheLab Desk",
                    style = TextStyle(
                        fontFamily = samsungSangFamily,
                        fontWeight = FontWeight.W600,
                        fontSize = 32.sp,
                        letterSpacing = 1.4.sp,
                        textAlign = TextAlign.Start,
                        color = Color.LightGray
                    ),
                )
            }
        }
    }
}


//////////////////////////////////////////////////
//
// PREVIEWS
//
//////////////////////////////////////////////////