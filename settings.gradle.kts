enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "HH-Quickly-Searcher"

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        id("androidx.navigation.safeargs.kotlin") version "2.8.9"
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

includeBuild("build-logic")

include(":app")
