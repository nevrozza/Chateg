plugins {
    id("android-setup")
    id("multiplatform-setup")
    id("kotlin-parcelize")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":common:auth:api"))
                implementation(project(":common:auth:presentation"))
                implementation(project(":common:main:api"))
                implementation(project(":common:main:presentation"))

                implementation(project(":common:core"))


                implementation(Dependencies.Kodein.core)
            }
        }
    }
}