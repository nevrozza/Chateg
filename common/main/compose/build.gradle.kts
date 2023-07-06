plugins {
    id("android-setup")
    id("multiplatform-compose-setup")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":common:main:api"))
                implementation(project(":common:main:presentation"))
            }
        }
    }
}