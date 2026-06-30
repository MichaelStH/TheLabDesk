import com.riders.thelabdesk.config.Configuration
import com.riders.thelabdesk.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.compose.desktop.DesktopExtension
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.compose.resources.ResourcesExtension

class ApplicationJvmComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("thelab-desk-application")
            apply("thelab-desk-compose")
        }


        extensions.configure<ComposeExtension>("compose") {
            configure<DesktopExtension> {
                application {
                    mainClass = "${Configuration.PACKAGE_NAME}.TheLabDeskMainKt"

                    nativeDistributions {
                        targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                        packageName = Configuration.APP_NAME
                        packageVersion = Configuration.version.toString()
                        description = "TheLab Desk App"
                        copyright = "© 2023 TheLab. All rights reserved."
                        vendor = "TheLab Inc."

                        macOS {
                            iconFile.set(project.file("$projectDir/src/main/resources/icons/thelab_desk.icns"))
                        }
                        windows {
                            iconFile.set(project.file("$projectDir/src/main/resources/icons/thelab_desk.ico"))
                        }
                        linux {
                            iconFile.set(project.file("$projectDir/src/main/resources/icons/thelab_desk.png"))
                        }
                    }
                }
            }
        }
    }
}