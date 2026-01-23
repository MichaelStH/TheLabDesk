import com.riders.thelabdesk.configureKotlinJvm
import com.riders.thelabdesk.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class ApplicationJvmConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.findPlugin("jetbrains-kotlin-jvm").get().get().pluginId)
            apply(libs.findPlugin("jetbrains-kotlin-serialization").get().get().pluginId)
            apply(libs.findPlugin("java-fx").get().get().pluginId)
        }

        configureKotlinJvm()

        dependencies {
            // Logging : Arbor
            add("implementation", libs.findLibrary("arbor").get())
        }
    }
}