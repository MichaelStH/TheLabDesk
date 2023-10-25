package core.compose.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.compose.utils.Text

//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@Composable
@Preview
fun ScrollableWindowContent(modifier: Modifier, content: @Composable () -> Unit) {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .then(modifier)
        ) {
            val stateVertical = rememberScrollState(0)
            val stateHorizontal = rememberScrollState(0)

            content()

            VerticalScrollbar(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight(),
                adapter = rememberScrollbarAdapter(stateVertical)
            )

            HorizontalScrollbar(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(end = 12.dp),
                adapter = rememberScrollbarAdapter(stateHorizontal)
            )
        }
    }
}

@Composable
@Preview
fun ScrollableWindowContent(lazyListState: LazyListState, content: @Composable () -> Unit) {
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(color = Color(180, 180, 180))
                .padding(10.dp)
        ) {
            val stateHorizontal = rememberScrollState(0)

            content()

            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd)
                    .fillMaxHeight(),
                adapter = rememberScrollbarAdapter(lazyListState)
            )
            HorizontalScrollbar(
                modifier = Modifier.align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(end = 12.dp),
                adapter = rememberScrollbarAdapter(stateHorizontal)
            )
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
private fun ScrollableWindowContent() {
    MaterialTheme {
        ScrollableWindowContent(modifier = Modifier) {
            Column { Text("Text test in preview scrollable") }
        }
    }
}