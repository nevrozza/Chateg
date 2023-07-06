plugins {
    id("android-setup")
    id("multiplatform-compose-setup")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":common:auth:api"))
                implementation(project(":common:auth:presentation"))
            }
        }
    }
}