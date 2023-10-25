package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Card(colors = CardDefaults.cardColors(containerColor = Color.LightGray), shape = RoundedCornerShape(35.dp)) {
            Column(
                modifier = Modifier.background(Color.LightGray).padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Image(
                    modifier = Modifier.size(64.dp),
                    painter = painterResource(resourcePath = "images/ic_lab.png"),
                    contentDescription = "logo_icon"
                )

                Text(text = "TheLab Desk", style = TextStyle(fontSize = 22.sp))

                Text(text = "1.0.0 (build 000.000.001-01)")

                Button(
                    modifier = Modifier.widthIn(50.dp, 150.dp),
                    onClick = { viewModel.updateShouldShowAboutDialog(false) }) {
                    Text(text = "OK")
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