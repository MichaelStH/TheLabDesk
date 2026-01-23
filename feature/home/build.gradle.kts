plugins {
    alias(libs.plugins.thelabdesk.feature.compose)
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:ui"))
    implementation(project(":core:video"))
}
