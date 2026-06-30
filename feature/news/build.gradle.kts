plugins {
    alias(libs.plugins.thelabdesk.feature.compose)
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
}