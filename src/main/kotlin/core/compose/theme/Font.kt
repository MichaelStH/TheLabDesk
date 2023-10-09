package core.compose.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font

val samsungSangFamily = FontFamily(
    Font(resource = "font/samsungsans_thin.ttf", weight = FontWeight.Thin),
    Font(resource = "font/samsungsans_light.ttf", weight = FontWeight.Light),
    Font(resource = "font/samsungsans_regular.ttf", weight = FontWeight.Normal),
    Font(resource = "font/samsungsans_italic.ttf", weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(resource = "font/samsungsans_medium.ttf", weight = FontWeight.Medium),
    Font(resource = "font/samsungsans_bold.ttf", weight = FontWeight.Bold)
)