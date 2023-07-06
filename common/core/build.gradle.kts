plugins {
    id("android-setup")
    id("multiplatform-setup")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(Dependencies.Kotlin.Coroutines.core)

                implementation(Dependencies.Settings.core)
                implementation(Dependencies.Settings.noargs)

                api(Dependencies.Kodein.core)
            }
        }
    }
}