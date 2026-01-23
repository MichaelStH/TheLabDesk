package com.riders.thelabdesk

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.compose.ExperimentalComposeLibrary

@OptIn(ExperimentalComposeLibrary::class)
fun Project.configureCompose(extension: ComposeExtension) {
    extension.apply {
        val composeDependencies = extension.dependencies

        dependencies {
            // Note, if you develop a library, you should use compose.desktop.common.
            // compose.desktop.currentOs should be used in launcher-sourceSet
            // (in a separate module for demo project and in testMain).
            // With compose.desktop.common you will also lose @Preview functionality
            add("implementation", composeDependencies.desktop.currentOs)
            add("implementation", composeDependencies.desktop.components.animatedImage)
            add("implementation", composeDependencies.desktop.components.splitPane)
            add("implementation", composeDependencies.animation)
            add("implementation", composeDependencies.animationGraphics)
            add("implementation", composeDependencies.components.resources)
            add("implementation", composeDependencies.material)
            add("implementation", composeDependencies.materialIconsExtended)
            add("implementation", composeDependencies.material3)
            add("implementation", composeDependencies.foundation)
            add("implementation", composeDependencies.html.core)
            add("implementation", composeDependencies.preview)
            add("implementation", composeDependencies.ui)
            add("implementation", composeDependencies.uiTooling)

            add("implementation", libs.findLibrary("compose-full").get())
        }
    }
}