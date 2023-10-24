package core.compose.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import core.compose.theme.TheLabDeskTheme

@Composable
fun DataProvidedBy(providerName: String, resImage: String? = null) {
    TheLabDeskTheme {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TheLabDeskText(modifier = Modifier, text = "Data provided by $providerName")
            if (null != resImage) {
                Image(
                    modifier = Modifier.widthIn(0.dp, 120.dp),
                    painter = painterResource(resImage),
                    contentDescription = "data provider logo",
                    contentScale = ContentScale.Fit
                )
            }

        }
    }
}

@Preview
@Composable
private fun PreviewDataProvidedBy() {
    TheLabDeskTheme {
        DataProvidedBy("TMDB")
    }
}