// Top-level build file where you can add configuration options common to all sub-projects/modules.
//buildscript {
//    dependencies {
//        classpath("com.google.gms:google-services:4.3.15")
//    }
//}
//buildscript {
//    repositories {
//        google()
//        mavenCentral()
//    }
//    dependencies {
//        classpath(libs.google.services)
//    }
//}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
//    id ("org.jetbrains.kotlin.android" ) version  "1.9.0" apply false
//    alias(libs.plugins.org.jetbrains.kotlin.kapt) apply false
    //dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.4.2" apply false

}
