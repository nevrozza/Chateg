plugins {
    id("android-setup")
    id("multiplatform-compose-setup")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":common:umbrella-core"))

                implementation(project(":common:main:compose"))
                implementation(project(":common:auth:compose"))
            }
        }

        androidMain {
            dependencies {
                implementation(Dependencies.Android.composeActivity)
            }
        }
    }
}