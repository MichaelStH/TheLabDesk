import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.util.*

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization")
    id("org.openjfx.javafxplugin")
}

group = "com.riders"
version = "1.0-SNAPSHOT"

val osName = System.getProperty("os.name")
val targetOs = when {
    osName == "Mac OS X" -> "macos"
    osName.startsWith("Win") -> "windows"
    osName.startsWith("Linux") -> "linux"
    else -> error("Unsupported OS: $osName")
}

val osArch = System.getProperty("os.arch")
var targetArch = when (osArch) {
    "x86_64", "amd64" -> "x64"
    "aarch64" -> "arm64"
    else -> error("Unsupported arch: $osArch")
}

val generatedVersionDir = "$projectDir/src/main/resources/generated-version"

val propertiesFile = file("$generatedVersionDir/version.properties")
propertiesFile.parentFile.mkdirs()
val properties = Properties()
properties.setProperty("version", rootProject.version.toString())
propertiesFile.writer().use { properties.store(it, null) }

sourceSets {
    main {
        output.dir(generatedVersionDir)
    }
}

kotlin {
    sourceSets.all {
        languageSettings {
            languageVersion = "2.0"
        }
    }
}

@OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
dependencies {

    // Kotlin
    implementation(platform(libs.kotlin.bom))
    kotlin("reflect")
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)

    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    implementation(compose.desktop.components.animatedImage)
    implementation(compose.desktop.components.splitPane)
    implementation(compose.animation)
    implementation(compose.animationGraphics)
    implementation(compose.components.resources)
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
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.serialization.kotlinx.json)


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

    // Types
    implementation(libs.kotools.types)

    // Zxing
    implementation(libs.zxing.core)
    implementation(libs.zxing.javase)

    // Logging : Arbor
    implementation(libs.arbor)

    /*
     * https://dev.to/tkuenneth/automatically-switch-to-dark-mode-and-back-in-compose-for-desktop-303l
     * As you can see, the color mode is stored in the Windows Registry and the macOS Defaults database.
     * To access both in Java or Kotlin I have written a tiny open source library called Native Parameter Store Acess.
     */
    implementation(libs.native.parameters.store.access)

    implementation("org.openjfx:javafx-web:21")
    implementation("org.jetbrains.jcef:jcef-skiko:0.1")
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
            macOS {
                iconFile.set(project.file("$projectDir/src/main/resources/icons/thelab_desk.icns"))
            }
            windows {
                iconFile.set(project.file("$projectDir/src/main/resources/icons/thelab_desk.ico"))
            }
            linux {
                iconFile.set(project.file("$projectDir/src/main/resources/icons/thelab_desk.png"))
            }
        }
    }
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.swing", "javafx.web", "javafx.graphics")
}

fun readProperties(propertiesFile: File) = Properties().apply {
    propertiesFile.inputStream().use { fis ->
        load(fis)
    }
}
