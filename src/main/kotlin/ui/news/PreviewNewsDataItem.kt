package ui.news

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.compose.component.TheLabDeskLogo
import core.compose.theme.TheLabDeskTheme
import core.compose.theme.md_theme_dark_error
import data.remote.dto.NewsDto

@Composable
fun NewsDataItem(modifier: Modifier, item: NewsDto) {
    TheLabDeskTheme {
        Card(
            modifier = Modifier.fillMaxWidth().height(360.dp).then(modifier),
            shape = RoundedCornerShape(22.dp)
        ) {

            Column(modifier = Modifier.fillMaxWidth()) {
                TheLabDeskLogo(modifier = Modifier.fillMaxWidth().weight(2f))

                // Bandeau
                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {

                    Box(
                        modifier = Modifier.fillMaxSize()
                            .background(
                                brush = Brush.horizontalGradient(
                                    listOf(
                                        Color.Black,
                                        Color.Black,
                                        Color.DarkGray
                                    )
                                )
                            )
                            .alpha(.8f)
                    )

                    Column(
                        modifier = Modifier.fillMaxSize().background(Color.Transparent),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item.title,
                                style = TextStyle(
                                    fontWeight = FontWeight.W400,
                                    fontSize = 24.sp,
                                    color = md_theme_dark_error
                                )
                            )

                            Card(modifier = Modifier.border(1.dp, Color.Black, RoundedCornerShape(16.dp))) {
                                Box(modifier = Modifier.padding(8.dp), contentAlignment = Alignment.Center) {
                                    Text(
                                        text = item.category,
                                        style = TextStyle(color = md_theme_dark_error)
                                    )
                                }
                            }
                        }

                        Text(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                            text = item.description,
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.White
                            ),
                            maxLines = 2
                        )

                        Text(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            text = item.date,
                            style = TextStyle(color = Color.LightGray),
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewNewsDataItem() {
    TheLabDeskTheme {
        NewsDataItem(
            modifier = Modifier,
            item = NewsDto(
                date = "2023-10-12-'T'-09:01:20",
                title = "Biometric",
                category = "TheLab",
                description = "New implementation of Biometric feature using Android built-in API",
                thumbnailUrl = ""
            )
        )
    }
}

