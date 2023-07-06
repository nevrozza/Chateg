pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Chateg"
include(":androidApp")
include(":desktopApp")

include(":common:core")
include(":common:umbrella-core")
include(":common:utils-compose")
include(":common:umbrella-compose")

include(":common:auth:api")
include(":common:auth:compose")
include(":common:auth:data")
include(":common:auth:presentation")

include(":common:main:api")
include(":common:main:compose")
include(":common:main:data")
include(":common:main:presentation")

include(":common:notifications")