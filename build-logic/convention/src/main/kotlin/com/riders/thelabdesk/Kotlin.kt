package com.riders.thelabdesk

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.*


/**
 * Configure base Kotlin options for JVM (non-Android)
 */
internal fun Project.configureKotlinJvm() {
    extensions.configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }

        // Up to Java 11 APIs are available through desugaring
        // https://developer.android.com/studio/write/java11-minimal-support-table
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    configure<KotlinProjectExtension> {
        sourceSets.all {
            languageSettings {
                languageVersion = "2.3"
            }
        }
    }

    configureKotlin<KotlinJvmProjectExtension>()

    dependencies {
        val kotlinBom = platform(libs.findLibrary("kotlin-bom").get())
        add("implementation", kotlinBom)
        add("implementation", libs.findLibrary("kotlin-reflect").get())
        add("implementation", libs.findLibrary("kotlin-stdlib").get())
        val kotlinxCoroutinesBom = platform(libs.findLibrary("kotlinx-coroutines-bom").get())
        add("implementation", kotlinxCoroutinesBom)
        add("implementation", libs.findLibrary("kotlinx-coroutines-core").get())
        add("implementation", libs.findLibrary("kotlinx-coroutines-swing").get())

        // Kotools Types
        add("implementation", libs.findLibrary("kotools-types").get())
    }
}

/**
 * Configure base Kotlin options
 */
private inline fun <reified T : KotlinBaseExtension> Project.configureKotlin() = configure<T> {
    // Treat all Kotlin warnings as errors (disabled by default)
    // Override by setting warningsAsErrors=true in your ~/.gradle/gradle.properties
    val warningsAsErrors = providers
        .gradleProperty("warningsAsErrors")
        .map { it.toBoolean() }
        .orElse(false)

    when (this) {
        is KotlinAndroidProjectExtension -> compilerOptions
        is KotlinJvmProjectExtension -> compilerOptions
        is KotlinMultiplatformExtension -> compilerOptions
        else -> TODO("Unsupported project extension $this ${T::class}")
    }.apply {

        jvmToolchain(21)

        //this.jvmTarget = JvmTarget.JVM_21
        //this.allWarningsAsErrors = warningsAsErrors
        this.freeCompilerArgs.addAll(
            "-opt-in=kotlin.RequiresOptIn",
            // Enable experimental coroutines APIs, including Flow
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlinx.coroutines.FlowPreview",
            "-opt-in=kotlin.Experimental",
        )
        this.freeCompilerArgs.add(
            // Enable experimental coroutines APIs, including Flow
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        )
        this.freeCompilerArgs.add(
            /*
             * Remove this args after Phase 3.
             * https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-consistent-copy-visibility/#deprecation-timeline
             *
             * Deprecation timeline
             * Phase 3. (Supposedly Kotlin 2.2 or Kotlin 2.3).
             * The default changes.
             * Unless ExposedCopyVisibility is used, the generated 'copy' method has the same visibility as the primary constructor.
             * The binary signature changes. The error on the declaration is no longer reported.
             * '-Xconsistent-data-class-copy-visibility' compiler flag and ConsistentCopyVisibility annotation are now unnecessary.
             */

            "-Xconsistent-data-class-copy-visibility"
        )
    }
}
