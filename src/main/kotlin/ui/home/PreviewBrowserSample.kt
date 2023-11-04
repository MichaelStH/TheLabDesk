package ui.home

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@Preview
@Composable
fun BrowserSample() {
    val width = 400.dp
    val height = 200.dp
    val shape = RoundedCornerShape(16.dp)
    val title = "In App Browser Sample"
    val desc = "The LabDesk integrate an example of an in-app web browser."

    HomeSectionContent(title = title, description = desc, alternateContentSide = true) {
        Card(modifier = Modifier.width(width).height(height), shape = shape) {
            Column(
                modifier = Modifier.fillMaxSize().background(Color.Black).wrapContentSize().clip(shape = shape),
                horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
            ) {
                Image(modifier = Modifier.fillMaxSize(),painter = painterResource("images/sample_browser_webview.PNG"), contentDescription = null, contentScale = ContentScale.Crop)
            }
        }
    }
}