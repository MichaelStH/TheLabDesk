package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.compose.component.ScrollableWindowContent
import core.compose.utils.AsyncBitmapImageFromNetwork
import utils.Constants
import viewmodel.MainViewModel


//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@Composable
@Preview
fun App(viewModel: MainViewModel) {
    MaterialTheme {

        val state = rememberLazyListState()

        ScrollableWindowContent {
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
                        Text(text = viewModel.text)
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
private fun PreviewApp() {
    val viewModel: MainViewModel = MainViewModel()
    MaterialTheme {
        App(viewModel = viewModel)
    }
}
