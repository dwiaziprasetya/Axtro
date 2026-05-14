pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "Axtro"
include(":app")
include(":core-ui")
include(":feature-addtask")
include(":feature-task")
include(":feature-calendar")
include(":feature-welcome")
include(":feature-resetpassword")
include(":feature-home")
include(":feature-profile")
include(":feature-signin")
include(":feature-signup")
include(":core-navigation")
