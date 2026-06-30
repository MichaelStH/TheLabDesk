plugins {
    alias(libs.plugins.thelabdesk.library)
    alias(libs.plugins.thelabdesk.compose.desktop)
}

dependencies {
    implementation(project(":core:common"))
    // TODO : Doesn't respect clean architecture. Remove when done
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:video"))
}