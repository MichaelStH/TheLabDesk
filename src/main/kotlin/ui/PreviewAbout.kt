package ui

import TheLabDeskApp
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.compose.component.TheLabDeskCard
import core.compose.theme.TheLabDeskTheme
import core.compose.theme.currentTheme
import core.compose.theme.isSystemInDarkTheme
import core.compose.utils.getColorScheme
import core.utils.BarcodeManager
import core.utils.DisplayManager
import di.AppModule
import kotlinx.coroutines.launch
import ui.main.MainViewModel
import utils.Constants


//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@Composable
fun About(viewModel: MainViewModel) {
    val scope = rememberCoroutineScope()
    var image: ImageBitmap? by remember { mutableStateOf(null) }

    // Calculate screen height to set height for card
    val cardHeight: Dp = (DisplayManager.getScreenHeight() - (DisplayManager.getScreenHeight() / 2)).dp

    scope.launch {
        image = BarcodeManager.generateQrCodeBitmap(Constants.QR_CODE_DATA_THE_LAB_DESK_GITHUB)?.toComposeImageBitmap()
            ?: loadImageBitmap(
                TheLabDeskApp.javaClass.getResourceAsStream("images/ic_lab.png")!!
            )
    }

    TheLabDeskTheme {
        TheLabDeskCard(modifier = Modifier.height(cardHeight), shape = RoundedCornerShape(35.dp)) {
            Row(modifier = Modifier.fillMaxWidth(.6f).fillMaxHeight()) {

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

                // Separator view
                Box(modifier = Modifier.width(2.dp).fillMaxHeight().background(Color.White))

                Column(
                    modifier = Modifier.fillMaxHeight().weight(1f).padding(20.dp),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Title & Version
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(32.dp)
                    ) {
                        Text(
                            modifier = Modifier,
                            text = Constants.APP_NAME,
                            style = TextStyle(fontSize = 22.sp)
                        )

                        //Version
                        Text(text = TheLabDeskApp.getVersion())

                        //Link
                        Text(text = "You can find this project on Github by scanning the QR Code below")
                    }

                    // QrCode to Github
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(color = if (!isSystemInDarkTheme()) currentTheme.first.getColorScheme().background else currentTheme.second.getColorScheme().background)
                            .clip(shape = RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        image?.let {
                            Image(
                                modifier = Modifier.align(Alignment.Center).clip(shape = RoundedCornerShape(16.dp)),
                                bitmap = it,
                                contentDescription = "github_barcode"
                            )
                        } ?: CircularProgressIndicator()
                    }

                    // Close button
                    Button(
                        modifier = Modifier.widthIn(50.dp, 150.dp),
                        onClick = { viewModel.updateShouldShowAboutDialog(false) }) {
                        Text(text = Constants.PLACEHOLDER_OK)
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