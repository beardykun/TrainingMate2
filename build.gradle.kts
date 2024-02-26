buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.tools.build.gradle)
        classpath(libs.google.services)
        classpath(libs.firebase.crashlytics.gradle)
        classpath(libs.moko.resources.generator)
    }
}

plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.library).apply(false)
    kotlin("android").version(libs.versions.kotlin.version.get()).apply(false)
    kotlin("multiplatform").version(libs.versions.kotlin.version.get()).apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
