package utils

import kotlin.math.roundToInt

fun Float.toPercentage(): Int = (this * 100).roundToInt()