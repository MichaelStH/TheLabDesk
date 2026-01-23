import com.riders.thelabdesk.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class FeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.findPlugin("thelabdesk-library").get().get().pluginId)
            apply(libs.findPlugin("jetbrains-kotlin-serialization").get().get().pluginId)
        }

        dependencies {
            add("implementation", libs.findLibrary("kotlinx-serialization-json").get())
        }
    }
}