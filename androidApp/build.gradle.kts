plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.chateg.app.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.chateg.app.android"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":common:main:api"))
    implementation(project(":common:notifications"))

    implementation(project(":common:umbrella-compose"))
    implementation(project(":common:umbrella-core"))
    implementation(project(":common:core"))
    implementation(project(":common:utils-compose"))
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")
    implementation(Dependencies.Android.Compose.material3)
    implementation("androidx.compose.foundation:foundation:1.4.3")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(Dependencies.Decompose.decompose)
    implementation(Dependencies.Android.runtime)
    implementation("androidx.work:work-runtime-ktx:2.8.1")

}