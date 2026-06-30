plugins {
    alias(libs.plugins.thelabdesk.library)
}

dependencies {
    implementation(project(":core:common"))

    // Ktor
    implementation(platform(libs.ktor.bom))
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.serialization.kotlinx.json)
}