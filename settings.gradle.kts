rootProject.name = "Formula1KMP"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev/")
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
        maven("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev/")
    }
}

include(":app")

include(":core:network")
include(":core:model")
include(":core:ui")
include(":core:utils")

include(":feature:splash:presentation")

include(":feature:starter:di")
include(":feature:starter:data")
include(":feature:starter:domain")
include(":feature:starter:presentation")

include(":feature:pager:presentation")

include(":feature:drivers:di")
include(":feature:drivers:data")
include(":feature:drivers:domain")
include(":feature:drivers:presentation")

include(":feature:teams:di")
include(":feature:teams:data")
include(":feature:teams:domain")
include(":feature:teams:presentation")

include(":androidApp")
include(":desktopApp")
include(":webApp")

