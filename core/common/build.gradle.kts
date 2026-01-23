plugins {
    alias(libs.plugins.thelabdesk.library)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.native.parameters.store.access)

    implementation(libs.org.json)
    implementation(libs.jackson.core)
    implementation(libs.jackson.dataformat)
}