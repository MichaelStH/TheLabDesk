plugins {
    alias(libs.plugins.thelabdesk.feature.compose)
    alias(libs.plugins.thelabdesk.javafx)
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:ui"))
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.swing", "javafx.web", "javafx.graphics")
}

