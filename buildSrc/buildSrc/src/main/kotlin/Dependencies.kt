object Dependencies {
    object Kodein {
        const val core = "org.kodein.di:kodein-di:7.20.1"
    }

    object Settings {
        const val core = "com.russhwolf:multiplatform-settings:1.0.0-RC"
        const val noargs = "com.russhwolf:multiplatform-settings-no-arg:1.0.0-RC"
    }

    object Kotlin {
        private const val version = "1.8.20"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val jsoup = "org.jsoup:jsoup:1.14.3"

        object Coroutines {
            private const val version = "1.7.0-RC"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        }
    }

    object Compose {
        private const val version = "1.4.0"
        const val gradlePlugin = "org.jetbrains.compose:compose-gradle-plugin:$version"
    }

    object Android {
        private const val version = "8.0.1"
        const val gradlePlugin = "com.android.tools.build:gradle:$version"
        const val composeActivity = "androidx.activity:activity-compose:1.7.0"
        const val runtime = "androidx.compose.runtime:runtime:1.4.3"
        object Compose {
            const val ui = "androidx.compose.ui:ui:1.4.3"
            const val material3 = "androidx.compose.material3:material3:1.1.0"
            const val icons  = "androidx.compose.material:material-icons-core:1.4.3"
            const val tooling  = "androidx.compose.ui:ui-tooling:1.4.3"

        }
    }

    object Decompose {
        private const val version = "2.0.0"
        const val decompose = "com.arkivanov.decompose:decompose:$version"
        const val androidCompose = "com.arkivanov.decompose:extensions-compose-jetpack:$version"
        const val compose = "com.arkivanov.decompose:extensions-compose-jetbrains:$version"
    }

}