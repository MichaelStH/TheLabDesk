plugins {
    alias(libs.plugins.thelabdesk.library)
    alias(libs.plugins.thelabdesk.compose.desktop)
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:ui"))

    /* https://proandroiddev.com/unifying-video-players-compose-multiplatform-for-ios-android-desktop-aa920d29bbf3 */
    implementation(libs.vlc.player)
    implementation(libs.vlc.java.fx)
    implementation(libs.vlc.info)
    implementation(libs.vlc.natives)
    implementation(libs.vlc.osx)
}