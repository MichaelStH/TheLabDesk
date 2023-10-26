package ui

import TheLabDeskApp
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.compose.component.TheLabDeskCard
import core.compose.theme.TheLabDeskTheme
import di.AppModule
import ui.main.MainViewModel


//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@Composable
fun About(viewModel: MainViewModel) {
    TheLabDeskTheme {
        TheLabDeskCard(modifier = Modifier, shape = RoundedCornerShape(35.dp)) {
            Row(modifier = Modifier.fillMaxSize(.5f)) {
                Box(modifier = Modifier.weight(.75f)) {

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
                        modifier = Modifier.fillMaxHeight().align(Alignment.Center).padding(35.dp),
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
                }

                Box(modifier = Modifier.width(2.dp).fillMaxHeight().background(Color.White))

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Image(
                        modifier = Modifier.size(64.dp),
                        painter = painterResource(resourcePath = "images/ic_lab.png"),
                        contentDescription = "logo_icon"
                    )

                    Text(text = "TheLab Desk", style = TextStyle(fontSize = 22.sp))

                    Text(text = TheLabDeskApp.getVersion())

                    Button(
                        modifier = Modifier.widthIn(50.dp, 150.dp),
                        onClick = { viewModel.updateShouldShowAboutDialog(false) }) {
                        Text(text = "OK")
                    }
                }
            }
        }
    }
}

//////////////////////////////////////////////////
//
// PREVIEWS
//
//////////////////////////////////////////////////
@Preview
@Composable
private fun About() {
    val viewModel = MainViewModel(AppModule.injectDependencies())

    TheLabDeskTheme {
        About(viewModel)
    }
}