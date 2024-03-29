// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("io.realm:realm-gradle-plugin:10.11.1")
        classpath("io.realm:realm-gradle-plugin:10.14.0-transformer-api")
    }
}

plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    kotlin("plugin.serialization") version "1.9.22" apply false
    id("org.jetbrains.kotlin.kapt") version "1.6.20" apply false
    id("io.realm.kotlin") version "1.11.0" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}