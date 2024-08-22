import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalDistributionDsl
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {

    val copyJsResources = tasks.create("copyJsResourcesWorkaround", Copy::class.java) {
        from(project(":app").file("src/commonMain/composeResources"))
        into("build/processedResources/js/main")
    }

    val copyWasmResources = tasks.create("copyWasmResourcesWorkaround", Copy::class.java) {
        from(project(":app").file("src/commonMain/composeResources"))
        into("build/processedResources/wasmJs/main")
    }

    afterEvaluate {
        project.tasks.getByName("jsProcessResources").finalizedBy(copyJsResources)
        project.tasks.getByName("wasmJsProcessResources").finalizedBy(copyWasmResources)
        project.tasks.getByName("wasmJsDevelopmentExecutableCompileSync").dependsOn(copyWasmResources)
        project.tasks.getByName("jsDevelopmentExecutableCompileSync").mustRunAfter(copyJsResources)
        project.tasks.getByName("wasmJsDevelopmentExecutableCompileSync").mustRunAfter(copyWasmResources)
        project.tasks.getByName("jsProductionExecutableCompileSync").mustRunAfter(copyJsResources)
        project.tasks.getByName("wasmJsProductionExecutableCompileSync").mustRunAfter(copyWasmResources)
        //project.tasks.getByName("wasmDevelopmentExecutableCompileSync").mustRunAfter(copyWasmResources)
        project.tasks.getByName("wasmJsDevelopmentExecutableCompileSync").dependsOn(copyWasmResources)
    }

    js(IR) {
        moduleName = "webApp"
        browser {
            commonWebpackConfig {
                outputFileName = "webApp.js"
            }
        }
        binaries.executable()
    }

    wasmJs {
        moduleName = "webApp"
        browser {
            commonWebpackConfig {
                outputFileName = "webApp.js"
            }
        }
        binaries.executable()
    }

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        languageVersion.set(KotlinVersion.KOTLIN_2_0)
    }

    sourceSets {

        val jsWasmMain by creating {
            dependencies {
                implementation(project(":app"))
                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation(libs.bundles.koin.common)
            }
        }

        val jsMain by getting {
            dependsOn(jsWasmMain)
        }

        val wasmJsMain by getting {
            dependsOn(jsWasmMain)
        }

    }
}

compose.experimental {
    web.application {}
}