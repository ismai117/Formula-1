import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    application
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(libs.bundles.koin.common)
    implementation(project(":app"))
}

application {
    mainClass.set("MainKt")
}