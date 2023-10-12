package core.compose.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import core.compose.theme.TheLabDeskTheme

@Composable
fun TheLabDeskLogo() {
    TheLabDeskTheme {
        Box(modifier = Modifier, contentAlignment = Alignment.Center) {
            Image(
                modifier = Modifier.size(64.dp).padding(start = 20.dp),
                painter = painterResource(resourcePath = "images/ic_lab.png"),
                contentDescription = "logo_icon"
            )
        }

    }
}

@Composable
fun TheLabDeskLogo(modifier: Modifier) {
    TheLabDeskTheme {
        Box(modifier = Modifier, contentAlignment = Alignment.Center) {
            Image(
                modifier = Modifier.then(modifier),
                painter = painterResource(resourcePath = "images/ic_lab.png"),
                contentDescription = "logo_icon"
            )
        }
    }
}


@Preview
@Composable
private fun PreviewTheLabDeskLogo() {
    TheLabDeskTheme {
        TheLabDeskLogo()
    }
}

@Preview
@Composable
private fun PreviewTheLabDeskLogoModifier() {
    TheLabDeskTheme {
        TheLabDeskLogo(modifier = Modifier.size(300.dp))
    }
}