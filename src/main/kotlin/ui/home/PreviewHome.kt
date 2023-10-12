package ui.home

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.compose.theme.TheLabDeskTheme
import core.compose.utils.AsyncBitmapImageFromNetwork
import di.AppModule
import ui.WelcomeContent
import utils.Constants
import viewmodel.MainViewModel


//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@Composable
fun Home(viewModel: MainViewModel) {
    val state = rememberLazyListState()

    TheLabDeskTheme {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 12.dp),
            state = state
        ) {

            item {
                WelcomeContent()
            }

            item {
                BoxWithConstraints(modifier = Modifier.fillMaxWidth(), Alignment.Center) {
                    Card(
                        modifier = Modifier.width(this.maxWidth / 1.5f).heightIn(0.dp, 300.dp).padding(20.dp),
                        shape = RoundedCornerShape(35.dp)
                    ) {
                        AsyncBitmapImageFromNetwork(
                            modifier = Modifier.fillMaxWidth(),
                            url = Constants.IMAGE_URL
                        )
                    }
                }
            }

            item {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModel.updateText("Hello, Desktop!")
                    }
                ) {
                    core.compose.utils.Text(text = viewModel.text)
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
private fun PreviewHome() {
    val viewModel: MainViewModel = MainViewModel(AppModule.injectDependencies())
    TheLabDeskTheme {
        Home(viewModel = viewModel)
    }
}
