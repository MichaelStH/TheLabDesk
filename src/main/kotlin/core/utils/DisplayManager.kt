package core.utils

import java.awt.Dimension

object DisplayManager {

    fun getScreenDimension(): Dimension = java.awt.Toolkit.getDefaultToolkit().screenSize
    fun getScreenWidth(): Int = getScreenDimension().width
    fun getScreenHeight(): Int = getScreenDimension().height
}