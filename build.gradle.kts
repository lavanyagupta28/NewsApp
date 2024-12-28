
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false

}
// Project-level build.gradle file
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Update to a compatible Android Gradle Plugin version
        classpath ("com.android.tools.build:gradle:8.2.0")
    }
}
