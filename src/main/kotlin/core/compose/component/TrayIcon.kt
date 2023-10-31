package core.compose.component

import TheLabDeskApp
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.loadImageBitmap
import core.log.Timber
import core.utils.SystemManager

object TheLabDeskIcon : Painter() {
    override val intrinsicSize = Size(256f, 256f)

    override fun DrawScope.onDraw() {
        val imageBitmap: ImageBitmap? =
            runCatching {
                TheLabDeskApp.javaClass.getResourceAsStream(
                    if (SystemManager.isMacOs()) "icons/thelab_desk.icns"
                    else if (SystemManager.isLinux()) "icons/thelab_desk.png"
                    else "icons/thelab_desk.ico"
                )?.let { loadImageBitmap(it) }
            }
                .onFailure {
                    Timber.e("runCatching | onFailure | error caught: ${it.message}")
                }
                .getOrNull()

        if (null != imageBitmap) {
            drawImage(imageBitmap)
        }
    }
}

object MyAppIcon : Painter() {
    override val intrinsicSize = Size(256f, 256f)

    override fun DrawScope.onDraw() {
        drawOval(Color.Green, Offset(size.width / 4, 0f), Size(size.width / 2f, size.height))
        drawOval(Color.Blue, Offset(0f, size.height / 4), Size(size.width, size.height / 2f))
        drawOval(Color.Red, Offset(size.width / 4, size.height / 4), Size(size.width / 2f, size.height / 2f))
    }
}

object TrayIcon : Painter() {
    override val intrinsicSize = Size(256f, 256f)

    override fun DrawScope.onDraw() {
        drawOval(Color(0xFFFFA500))
    }
}