pluginManagement {

    includeBuild("build-logic")


    repositories {
        gradlePluginPortal()
        google()

        maven("https://jitpack.io")
        maven("https://plugins.gradle.org/m2/")

        mavenCentral()

        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
        maven("https://packages.jetbrains.team/maven/p/firework/dev")
        // Temp
        maven("https://packages.jetbrains.team/maven/p/ui/dev")
        // Ktor Early Access Program Repository
        maven("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
        maven("https://androidx.dev/storage/compose-compiler/repository")

        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }

    plugins {
        kotlin("jvm").version(extra["kotlin.version"] as String)
        id("org.jetbrains.compose").version(extra["compose.version"] as String)
        kotlin("plugin.serialization").version(extra["kotlin.version"] as String)
        id("org.openjfx.javafxplugin") version "0.0.10"
        id("org.jetbrains.kotlin.plugin.compose").version(extra["kotlin.version"] as String)
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()


        maven("https://jitpack.io")
        maven("https://plugins.gradle.org/m2/")

        mavenCentral()
        mavenLocal()

        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
        maven("https://packages.jetbrains.team/maven/p/firework/dev")
        // Temp
        maven("https://packages.jetbrains.team/maven/p/ui/dev")
        // Ktor Early Access Program Repository
        maven("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
        maven("https://androidx.dev/storage/compose-compiler/repository")

        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

rootProject.name = "LabDesk"
include(":core:common")
include(":core:data")
include(":core:domain")
include(":core:ui")
include(":feature:home")
include(":feature:splashscreen")