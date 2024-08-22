import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinx.serialization)
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
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.kstore.file)
            implementation(libs.ktor.client.android)
            implementation(libs.koin.android)
            implementation("androidx.startup:startup-runtime:1.1.1")
        }

        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization)
            implementation(libs.bundles.ktor.common)
            implementation(libs.bundles.koin.common)
            implementation(libs.napier)
            implementation(libs.kstore)

            implementation(project(":feature:teams:domain"))

            implementation(project(":core:network"))
            implementation(project(":core:model"))
            implementation(project(":core:utils"))

        }

        desktopMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
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
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}