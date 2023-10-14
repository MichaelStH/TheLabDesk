import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization")
}

group = "com.riders"
version = "1.0-SNAPSHOT"

dependencies {

    implementation(platform(libs.kotlin.bom))
    kotlin("reflect")
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)

    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    implementation(compose.animation)
    implementation(compose.animationGraphics)
    implementation(compose.material)
    implementation(compose.material3)
    implementation(compose.materialIconsExtended)
    implementation(compose.foundation)
    implementation(compose.html.core)
    implementation(compose.preview)
    implementation(compose.ui)
    implementation(compose.uiTooling)
    implementation(libs.compose.full)

    // Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)

    // Logging : Arbor
    implementation(libs.arbor)

    /*
     * https://dev.to/tkuenneth/automatically-switch-to-dark-mode-and-back-in-compose-for-desktop-303l
     * As you can see, the color mode is stored in the Windows Registry and the macOS Defaults database.
     * To access both in Java or Kotlin I have written a tiny open source library called Native Parameter Store Acess.
     */
    implementation("com.github.tkuenneth:nativeparameterstoreaccess:0.1.2")
}

tasks.wrapper {
    this.gradleVersion = "8.4"
    // You can either download the binary-only version of Gradle (BIN) or
    // the full version (with sources and documentation) of Gradle (ALL)
    distributionType = Wrapper.DistributionType.ALL
}

compose.desktop {
    application {
        mainClass = "TheLabDeskApp.kt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "LabDesk"
            packageVersion = "1.0.0"
        }
    }
}
