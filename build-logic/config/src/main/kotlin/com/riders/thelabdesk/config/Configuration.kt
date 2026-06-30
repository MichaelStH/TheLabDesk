package com.riders.thelabdesk.config

object Configuration {
    const val GROUP = "com.riders"

    const val PACKAGE_NAME_BUILD_LOGIC = "$GROUP.thelabdesk.buildlogic"
    const val PACKAGE_NAME = "$GROUP.thelabdesk"
    const val APP_NAME = "TheLabDesk"

    val version = Version(versionMajor = 1, versionMinor = 0, versionPatch = 0, isSnapshot = true)
}

data class Version(
    val versionMajor: Int,
    val versionMinor: Int,
    val versionPatch: Int,
    val isSnapshot: Boolean
) {
    override fun toString(): String = StringBuilder().apply {
        append(versionMajor)
        append('.')
        append(versionMinor)
        append('.')
        append(versionPatch)
        if (isSnapshot) {
            append('-')
            append("SNAPSHOT")
        }
    }
        .toString()
}