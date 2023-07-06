plugins {
    id("android-setup")
    id("multiplatform-compose-setup")
}



kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":common:utils-compose"))

                implementation(project(":common:main:api"))
                implementation(project(":common:main:presentation"))
            }
        }

        androidMain {
            dependencies {
                implementation("androidx.lifecycle:lifecycle-common:2.4.1")
            }
        }
    }
}