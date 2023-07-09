plugins {
    id("android-setup")
    id("multiplatform-setup")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":common:core"))
                implementation(project(":common:main:api"))
            }
        }
        androidMain {
            dependencies {
                implementation("androidx.core:core-ktx:1.10.1")
            }
        }
    }
}