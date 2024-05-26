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
        maven ( "https://jitpack.io" )

        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            if(requested.id.toString() == "com.github.takahirom.decomposer"){
                useModule("com.github.takahirom:decomposer:main-SNAPSHOT")
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }





}

rootProject.name = "This Day In History"
include(":app")
 