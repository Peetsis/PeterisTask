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
    }
}

rootProject.name = "PeterisTask"
include(":app")
include(":core")
include(":feature")
include(":core:database")
include(":core:network")
include(":core:designsystem")
include(":feature:list")
include(":feature:detail")
include(":core:model")
include(":core:data")
