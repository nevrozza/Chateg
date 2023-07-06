plugins {
    id("multiplatform-setup")
    id("android-setup")
    id("kotlin-parcelize")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(project(":common:auth:api"))
            }
        }
    }
}