pluginManagement {
    repositories {
        google()
        jcenter()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        jcenter()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "CloudEngineeringProject"
include(":app")
include(":auth:auth-api")
include(":auth:auth-ui")
include(":auth:auth-impl")
include(":core:core-api")
include(":core:core-ui")
