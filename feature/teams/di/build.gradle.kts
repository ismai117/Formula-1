import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
}

kotlin {

    js {
        browser()
    }

    wasmJs {
        browser()
    }
    
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm("desktop")

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        languageVersion.set(KotlinVersion.KOTLIN_2_0)
    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.kstore.file)
        }
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kstore)
            api(libs.kotlin.inject.runtime)

            implementation(project(":feature:teams:data"))
            implementation(project(":feature:teams:domain"))
            implementation(project(":feature:teams:presentation"))
        }

        desktopMain.dependencies {
            implementation(libs.kstore.file)
            implementation(libs.harawata.appdirs)
            implementation(libs.ktor.client.java)
        }

        iosMain.dependencies {
            implementation(libs.kstore.file)
            implementation(libs.ktor.client.darwin)
        }

        jsMain.dependencies {
            implementation(libs.kstore.storage)
            implementation(libs.ktor.client.js)
        }

        wasmJsMain.dependencies {
            implementation(libs.kstore.storage)
        }
    }

}

android {
    namespace = "org.ncgroup.formula1kmp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}