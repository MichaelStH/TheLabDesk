import com.riders.thelabdesk.configureCompose
import com.riders.thelabdesk.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.compose.resources.ResourcesExtension
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag


class ComposeDesktopConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("jetbrains-compose").get().get().pluginId)
                apply(libs.findPlugin("compose-compiler").get().get().pluginId)
            }

            extensions.configure<ComposeCompilerGradlePluginExtension> {
                this.featureFlags.addAll(ComposeFeatureFlag.OptimizeNonSkippingGroups)
                this.reportsDestination.set(layout.buildDirectory.dir("compose_compiler"))
            }

            extensions.configure<ComposeExtension>("compose") {
                configure<ResourcesExtension> {
                    generateResClass = always
                    publicResClass = true
                }

                configureCompose(this)
            }
        }
    }
}