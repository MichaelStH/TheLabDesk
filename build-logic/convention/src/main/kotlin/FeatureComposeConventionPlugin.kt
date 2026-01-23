import com.riders.thelabdesk.configureCompose
import com.riders.thelabdesk.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.compose.ComposeExtension

class FeatureComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.findPlugin("thelabdesk-library").get().get().pluginId)
            apply(libs.findPlugin("thelabdesk-compose-desktop").get().get().pluginId)
            apply(libs.findPlugin("jetbrains-kotlin-serialization").get().get().pluginId)
        }

        extensions.configure<ComposeExtension> {
            configureCompose(this)
        }

        dependencies {
            add("implementation", libs.findLibrary("kotlinx-serialization-json").get())
        }
    }
}