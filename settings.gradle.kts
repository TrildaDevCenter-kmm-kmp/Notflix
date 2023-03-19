pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        maven { url = uri("https://jitpack.io") }
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
        maven { url = uri("https://jitpack.io") }
    }
}

include(":app-android")
include(":shared")
include(":app-desktop")

rootProject.name = "Notflix"
