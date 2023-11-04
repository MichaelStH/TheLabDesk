package core.compose.component.dynamicisland

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import core.compose.component.TheLabDeskSurface
import core.compose.component.TheLabDeskText
import core.compose.theme.TheLabDeskTheme
import core.compose.theme.isSystemInDarkTheme
import di.AppModule
import ui.main.MainViewModel

@Preview
@Composable
fun Welcome() {
    Row(
        modifier = Modifier.padding(16.dp).fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TheLabDeskText(modifier = Modifier, text = "Welcome to ")

        Image(
            modifier = Modifier.height(16.dp),
            painter = painterResource(resourcePath = "images/ic_lab_6_the.xml"),
            contentDescription = "the_icon",
            colorFilter = ColorFilter.tint(if (!isSystemInDarkTheme()) Color.Black else Color.White)
        )

        Image(
            modifier = Modifier.height(16.dp),
            painter = painterResource(resourcePath = "images/ic_lab_6_lab.xml"),
            contentDescription = "lab_icon",
            colorFilter = ColorFilter.tint(if (!isSystemInDarkTheme()) Color.Black else Color.White)
        )
    }
}

@Preview
@Composable
private fun PreviewWelcome() {
    val viewModel = MainViewModel(AppModule.injectDependencies())

    TheLabDeskTheme(viewModel.isDarkMode) {
        Column {
            // Light mode
            viewModel.updateDarkMode(false)
            TheLabDeskSurface(modifier = Modifier.fillMaxWidth().heightIn(0.dp, 500.dp)) {
                Welcome()
            }

            // Dark mode
            viewModel.updateDarkMode(true)
            TheLabDeskSurface(modifier = Modifier.fillMaxWidth().heightIn(0.dp, 500.dp)) {
                Welcome()
            }
        }
    }
}
