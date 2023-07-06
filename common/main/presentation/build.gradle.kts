plugins {
    id("multiplatform-setup")
    id("android-setup")
    id("kotlin-parcelize")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(project(":common:main:api"))
                implementation(project(":common:core"))
            }
        }
    }
}