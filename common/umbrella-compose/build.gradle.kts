plugins {
    id("android-setup")
    id("multiplatform-compose-setup")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":common:umbrella-core"))

                implementation(project(":common:utils-compose"))

                implementation(project(":common:main:api"))
                implementation(project(":common:main:compose"))
                implementation(project(":common:auth:api"))
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