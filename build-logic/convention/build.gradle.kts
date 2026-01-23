plugins {
    `kotlin-dsl`
}


val packageName = "com.riders.thelabdesk.buildlogic"
group = packageName


kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(project(":config"))

    compileOnly(gradleApi()) // Use gradleApi() for Gradle API access

    // We need the Kotlin Gradle Plugin to access its extensions
    // implementation("org.jetbrains.kotlin.multiplatform:org.jetbrains.kotlin.multiplatform.gradle.plugin:2.3.0")
    compileOnly(kotlin("gradle-plugin"))

    compileOnly(libs.plugins.jetbrains.kotlin.jvm.toDep())
    compileOnly(libs.plugins.jetbrains.kotlin.serialization.toDep())
    compileOnly(libs.jetbrains.compose.gradlePlugin)
    compileOnly(libs.plugins.jetbrains.compose.hotReload.toDep())
    compileOnly(libs.plugins.compose.compiler.toDep())
    // compileOnly(libs.ktor.gradlePlugin)
}

// Register the plugin with a custom ID
gradlePlugin {

    val root: String = "com.riders.thelabdesk"

    plugins {

        // Application JVM Compose Convention Plugin
        register("applicationJvmComposeConvention") {
            id = "$root.application.jvm.compose"
            implementationClass = "ApplicationJvmComposeConventionPlugin"
        }

        // Application JVM Convention Plugin
        register("applicationJvmConvention") {
            id = "$root.application.jvm"
            implementationClass = "ApplicationJvmConventionPlugin"
        }

        // Compose Desktop Convention Plugin
        register("composeDesktopConvention") {
            id = "$root.compose.desktop"
            implementationClass = "ComposeDesktopConventionPlugin"
        }

        // Feature Compose Convention Plugin
        register("featureComposeConvention") {
            id = "$root.feature.compose"
            implementationClass = "FeatureComposeConventionPlugin"
        }

        // Feature Convention Plugin
        register("featureConvention") {
            id = "$root.feature"
            implementationClass = "FeatureConventionPlugin"
        }

        // JavaFx Convention Plugin
        register("javaFxConvention") {
            id = "$root.javafx"
            implementationClass = "JavaFxConventionPlugin"
        }

        // Library Convention Plugin
        register("libraryConvention") {
            id = "$root.library"
            implementationClass = "LibraryConventionPlugin"
        }
    }
}


fun Provider<PluginDependency>.toDep(): Provider<String> = map {
    "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}"
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

val generateProjectBuildConfigTask by tasks.registering {
    group = "build setup"
    description = "Generate build config file"

    val outputDir = layout.projectDirectory.dir("src/generated/kotlin")
    outputs.dir(outputDir)
    outputs.upToDateWhen { false }

    fun buildConfigContent(packageName: String, value: String) = """
        package $packageName
    
        object BuildConfig {
            const val BUILD_TYPE: String = "$value"
        }
    """.trimIndent()

    doFirst {
        val rootTasks = gradle.parent?.startParameter?.taskNames.orEmpty()
        val regexes = listOf(
            Regex("""^assemble(.+)$"""),
            Regex("""^root(.+)UnitTest$"""),
            Regex("""^root(.+)CoverageReport$"""),
            Regex("""^root(.+)CoverageReport$"""),
            Regex("""^rootPublish(.+)ToMavenLocal$"""),
            Regex("""^rootValidate(.+)Api"""),
            Regex("""^rootUpdate(.+)Api"""),
        )

        val buildTypeFound = rootTasks
            .asSequence()
            .map { it.substringAfterLast(":") }
            .firstNotNullOfOrNull { task ->
                regexes.firstNotNullOfOrNull { regex ->
                    regex.matchEntire(task)?.groupValues?.get(1)
                }
            }
            ?.lowercase()

        val file = outputDir.file("com/tezov/tuucho/project/BuildConfig.kt").asFile
        file.parentFile.mkdirs()
        val buildTypeResolved: String? = when {
            !file.exists() -> buildTypeFound ?: "debug".also {
                println("⚠️ buildType not found and BuildConfig didn't exist → create BuildConfig.kt with 'debug' build type")
            }

            buildTypeFound == null -> null.also {
                println("⚠️ buildType not found but BuildConfig exist → keep BuildConfig.kt current build type")
            }

            else -> buildTypeFound
        }
        if (buildTypeResolved != null) {
            file.writeText(buildConfigContent(packageName, buildTypeResolved))
        }
        if (file.exists()) {
            val content = file.readText()
            val match = Regex("""const val BUILD_TYPE: String = "([^"]+)"""").find(content)
            val currentValue = match?.groupValues?.get(1)
            println("Current BuildConfig BUILD_TYPE = $currentValue")
        } else {
            error("BuildConfig.kt not found at ${file.absolutePath}")
        }
    }
}

tasks.named("checkKotlinGradlePluginConfigurationErrors") {
    dependsOn(generateProjectBuildConfigTask)
}

sourceSets["main"].kotlin.srcDir(generateProjectBuildConfigTask.map { it.outputs.files })