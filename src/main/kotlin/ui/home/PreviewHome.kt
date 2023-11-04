package ui.home

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import core.compose.component.AutoSlidingCarousel
import core.compose.component.TheLabDeskText
import core.compose.theme.TheLabDeskTheme
import core.compose.theme.Typography
import core.compose.utils.AsyncBitmapImageFromNetwork
import core.compose.utils.AsyncSvgImage

//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselHeader(viewModel: HomeViewModel) {
    TheLabDeskTheme {
        Card(
            modifier = Modifier.fillMaxWidth().heightIn(0.dp, 350.dp).padding(vertical = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            AutoSlidingCarousel(
                modifier = Modifier.fillMaxSize(),
                itemsCount = viewModel.carouselList.size,
                itemContent = { index ->
                    BoxWithConstraints(
                        modifier = Modifier.fillMaxSize().padding(0.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        when (index) {
                            0 -> {
                                // Load home screen image welcoming
                                WelcomeContent()
                            }

                            1 -> {
                                AsyncSvgImage(
                                    url = viewModel.carouselList[index],
                                    density = LocalDensity.current
                                )
                            }

                            4 -> {
                                AsyncSvgImage(
                                    url = viewModel.carouselList[index],
                                    density = LocalDensity.current
                                )
                            }

                            else -> {
                                AsyncBitmapImageFromNetwork(
                                    modifier = Modifier.fillMaxWidth().height(this.maxHeight).padding(0.dp),
                                    url = viewModel.carouselList[index],
                                    contentScale = ContentScale.FillBounds
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun HomeSectionContent(
    title: String,
    description: String,
    alternateContentSide: Boolean = false,
    content: @Composable () -> Unit
) {
    TheLabDeskTheme {
        Column(
            modifier = Modifier.fillMaxWidth().height(380.dp).padding(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            TheLabDeskText(modifier = Modifier.width(600.dp), text = title, style = Typography.titleMedium)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!alternateContentSide) {
                    // Display description then content
                    TheLabDeskText(
                        modifier = Modifier.weight(2f),
                        text = description,
                        style = TextStyle(textAlign = TextAlign.Start),
                        maxLines = 10
                    )

                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        content()
                    }

                } else {
                    // Display content then description
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        content()
                    }

                    TheLabDeskText(
                        modifier = Modifier.weight(2f),
                        text = description,
                        style = TextStyle(textAlign = TextAlign.Start),
                        maxLines = 10
                    )
                }
            }
        }
    }
}


@Composable
fun Home(viewModel: HomeViewModel) {
    val listState = rememberLazyListState()

    TheLabDeskTheme {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 12.dp),
            state = listState
        ) {
            item { CarouselHeader(viewModel) }

            item { VideoPlayerSample() }

            item { BrowserSample() }

            item { TheatersSample() }
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
    val viewModel = HomeViewModel()
    TheLabDeskTheme {
        Home(viewModel = viewModel)
    }
}
