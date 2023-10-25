package data.remote.dto

import java.io.Serializable

@kotlinx.serialization.Serializable
data class Config(val isDarkMode: Boolean): Serializable
