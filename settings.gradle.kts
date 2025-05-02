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

rootProject.name = "ContactsApp"
include(":app")
include(":common:ui")
include(":feature:contacts_list:domain")
include(":feature:contacts_list:data")
include(":feature:contacts_list:presentation")
include(":feature:contacts_list:navigation")
include(":feature:contacts_list:di")
