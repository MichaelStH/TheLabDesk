plugins {
    alias(libs.plugins.thelabdesk.library)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:domain"))

    // Kotlin
    implementation(libs.kotlinx.serialization.json)

    // Ktor
    implementation(platform(libs.ktor.bom))
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.serialization.kotlinx.json)
}