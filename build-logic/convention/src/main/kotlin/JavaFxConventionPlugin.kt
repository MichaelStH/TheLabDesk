import com.riders.thelabdesk.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class JavaFxConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("java-fx").get().get().pluginId)
            }

            dependencies {
                // JavaFX
                add("implementation", libs.findLibrary("javafx-web").get())

                // Jetbrains
                // JCEF
                add("implementation", libs.findLibrary("jetbrains-jcef-skiko").get())
            }
        }
    }
}