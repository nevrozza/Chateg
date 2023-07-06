plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

kotlin {
    android()
    jvm("desktop")

    sourceSets {
        commonMain {
            dependencies {
                api(Dependencies.Decompose.decompose)
            }
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
}