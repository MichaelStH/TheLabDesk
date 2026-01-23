package com.riders.thelabdesk

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

val Project.androidLibs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("androidLibs")

val isMacOs get() = System.getProperty("os.name").startsWith("Mac", ignoreCase = true)

internal fun Project.version(name: String) = libs.findVersion(name).get().requiredVersion

//internal fun Project.buildType() = BuildConfig.BUILD_TYPE

internal fun Project.jvmTarget() = JvmTarget.fromTarget(version("javaVersion"))

internal fun Project.javaVersionString() = jvmTarget().target

internal fun Project.javaVersionInt() = javaVersionString().toInt()

internal fun Project.javaVersion() = JavaVersion.toVersion(javaVersionInt())

internal fun Project.javaLanguageVersion() = JavaLanguageVersion.of(javaVersionInt())

internal fun Project.versionName() = version("versionName")
