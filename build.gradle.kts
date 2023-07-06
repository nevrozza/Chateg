plugins {
    //trick: for the same plugin versions in all sub-modules
//    id("com.android.application").version("7.4.2").apply(false)
//    id("com.android.library").version("7.4.2").apply(false)
//    kotlin("android").version("1.8.10").apply(false)
//    kotlin("multiplatform").version("1.8.10").apply(false)
}

buildscript {
    repositories {
        maven("https://gitlab.com/api/v4/projects/38224197/packages/maven")
        mavenCentral()
        mavenLocal()
        google()
        gradlePluginPortal()
    }
//
//    dependencies {
//        classpath("com.github.winterreisender:webviewko-jvm:0.6.0")
//        classpath("com.github.winterreisender:webviewko:0.6.0")
//    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
