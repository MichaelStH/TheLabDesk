package com.riders.thelabdesk.core.ui.utils

import java.awt.Dimension
import java.awt.Toolkit

object DisplayManager {

    fun getScreenDimension(): Dimension = Toolkit.getDefaultToolkit().screenSize
    fun getScreenWidth(): Int = getScreenDimension().width
    fun getScreenHeight(): Int = getScreenDimension().height
}