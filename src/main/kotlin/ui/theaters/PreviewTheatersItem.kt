package ui.theaters

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import core.compose.component.TheLabDeskCard
import core.compose.component.TheLabDeskText
import core.compose.theme.TheLabDeskTheme
import core.compose.theme.Typography
import core.compose.utils.AsyncBitmapImageFromNetworkWithModifier
import core.compose.utils.Text
import data.local.model.tmdb.MovieModel
import data.local.model.tmdb.TvShowsModel
import data.remote.dto.tmdb.MovieDto
import data.remote.dto.tmdb.toModel
import di.AppModule
import utils.Constants

@Composable
fun TheatersItemContent(title: String, overview: String, poster: String, backdrop: String, onClick: () -> Unit) {
    val (width, height) = (190.dp to 285.dp)
    var visible by remember { mutableStateOf(false) }

    val backdropUrl =
        "${Constants.BASE_URL_TMDB_IMAGE_W_500_ENDPOINT}${backdrop}"
    val posterUrl =
        "${Constants.BASE_URL_TMDB_IMAGE_W_500_ENDPOINT}${poster}"


    Row(modifier = Modifier.widthIn(width, width * 3).heightIn(0.dp, height)) {
        // Card
        TheLabDeskCard(
            modifier = Modifier
                .width(width)
                .heightIn(0.dp, height)
                .animateContentSize(),
            onClick = {
                onClick()
                visible = !visible
            }
        ) {
            Row(modifier = Modifier) {
                // Box container
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.BottomCenter
                ) {

                    // Background image
                    AsyncBitmapImageFromNetworkWithModifier(
                        modifier = Modifier
                            .width(width)
                            .heightIn(0.dp, height)
                            .zIndex(0f),
                        url = posterUrl
                    )

                    // Gradient
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(.4f)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Transparent,
                                        Color.Black,
                                        Color.Black
                                    )
                                )
                            )
                            .align(Alignment.BottomCenter)
                    ) {

                        // Text
                        TheLabDeskText(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 16.dp),
                            text = title,
                            style = TextStyle(textAlign = TextAlign.Center),
                            color = Color.White
                        )
                    }
                }
            }
        }

        AnimatedVisibility(modifier = Modifier.fillMaxHeight(), visible = visible) {
            Column(
                modifier = Modifier.fillMaxHeight().padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                // Text
                TheLabDeskText(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = "Overview",
                    style = Typography.titleLarge,
                    maxLines = 1
                )

                // Text
                TheLabDeskText(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = overview,
                    style = TextStyle(color = Color.White, textAlign = TextAlign.Start),
                    maxLines = 7
                )

                Button(modifier = Modifier, onClick = { visible = !visible }) {
                    Text(text = "Close")
                }
            }
        }
    }
}

@Composable
fun TheatersItem(viewModel: TheatersViewModel, movie: MovieModel, onClick: () -> Unit) {
    TheLabDeskTheme(viewModel.isDarkMode) {
        TheatersItemContent(
            title = movie.originalTitle.toString(),
            overview = movie.overview.toString(),
            poster = movie.poster.toString(),
            backdrop = movie.backdropPath.toString(),
            onClick = onClick
        )
    }
}

@Composable
fun TheatersItem(viewModel: TheatersViewModel, tvShow: TvShowsModel, onClick: () -> Unit) {
    TheLabDeskTheme(viewModel.isDarkMode) {
        TheatersItemContent(
            title = tvShow.originalTitle.toString(),
            overview = tvShow.overview.toString(),
            poster = tvShow.poster.toString(),
            backdrop = tvShow.backdropPath.toString(),
            onClick = onClick
        )
    }
}


@Preview
@Composable
private fun PreviewTheatersItem() {
    val viewModel = TheatersViewModel(AppModule.injectDependencies())
    viewModel.updateDarkMode(true)
    val movie = MovieDto.getMockMovie().toModel()

    TheLabDeskTheme(viewModel.isDarkMode) {
        TheatersItem(viewModel, movie) {}
    }
}