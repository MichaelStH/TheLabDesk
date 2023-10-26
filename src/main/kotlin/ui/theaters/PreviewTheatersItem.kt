package ui.theaters

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import core.compose.utils.AsyncBitmapImageFromNetworkWithModifier
import data.local.model.tmdb.MovieModel
import data.local.model.tmdb.TvShowsModel
import data.remote.dto.tmdb.MovieDto
import data.remote.dto.tmdb.toModel
import di.AppModule
import utils.Constants

@Composable
fun TheatersItem(viewModel: TheatersViewModel, movie: MovieModel) {
    val (width, height) = (190.dp to 285.dp)

    val poster =
        "${Constants.BASE_URL_TMDB_IMAGE_W_500_ENDPOINT}${movie.poster}"

    TheLabDeskTheme(viewModel.isDarkMode) {

        // Card
        TheLabDeskCard(
            modifier = Modifier
                .width(width)
                .heightIn(0.dp, height),
            onClick = {}
        ) {

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
                    url = poster
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
                        text = movie.originalTitle.toString(),
                        style = TextStyle(color = Color.White, textAlign = TextAlign.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun TheatersItem(viewModel: TheatersViewModel, tvShow: TvShowsModel) {
    var visible by remember { mutableStateOf(false) }
    val (width, height) = (190.dp to 285.dp)

    val poster =
        "${Constants.BASE_URL_TMDB_IMAGE_W_500_ENDPOINT}${tvShow.poster}"

    TheLabDeskTheme(viewModel.isDarkMode) {
        // Card
        TheLabDeskCard(
            modifier = Modifier
                .width(width)
                .heightIn(0.dp, height),
            onClick = {
                visible = !visible
            }
        ) {
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
                    url = poster
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
                        text = tvShow.originalTitle.toString(),
                        style = TextStyle(color = Color.White, textAlign = TextAlign.Center)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun PreviewTheatersItem() {
    val viewModel = TheatersViewModel(AppModule.injectDependencies())
    viewModel.updateDarkMode(true)
    val movie = MovieDto.getMockMovie().toModel()

    TheLabDeskTheme(viewModel.isDarkMode) {
        TheatersItem(viewModel, movie)
    }
}