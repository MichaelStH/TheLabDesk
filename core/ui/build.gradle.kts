plugins {
    alias(libs.plugins.thelabdesk.library)
    alias(libs.plugins.thelabdesk.compose.desktop)
    alias(libs.plugins.thelabdesk.javafx)
}

dependencies {
    implementation(project(":core:common"))

    // Ktor
    implementation(platform(libs.ktor.bom))
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.serialization.kotlinx.json)

    // JNA
    api(libs.jna)
    api(libs.jna.platform)

    // https://mvnrepository.com/artifact/jakarta.json/jakarta.json-api
    implementation(libs.jakarta.json)
    // https://mvnrepository.com/artifact/org.json/json
    implementation(libs.org.json)
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
    implementation(libs.jackson.core)
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-xml
    implementation(libs.jackson.dataformat)

    /* https://proandroiddev.com/unifying-video-players-compose-multiplatform-for-ios-android-desktop-aa920d29bbf3 */
    implementation(libs.vlc.player)
    implementation(libs.vlc.java.fx)
    implementation(libs.vlc.info)
    implementation(libs.vlc.natives)
    implementation(libs.vlc.osx)

    // Zxing
    api(libs.zxing.core)
    api(libs.zxing.javase)


    /*
     * https://dev.to/tkuenneth/automatically-switch-to-dark-mode-and-back-in-compose-for-desktop-303l
     * As you can see, the color mode is stored in the Windows Registry and the macOS Defaults database.
     * To access both in Java or Kotlin I have written a tiny open source library called Native Parameter Store Access.
     */
    implementation(libs.native.parameters.store.access)
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.swing", "javafx.web", "javafx.graphics")
}

