plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")

//    id("com.google.gms.google-services")
}

android {
// to implement binding
    buildFeatures{
        viewBinding= true
    }
    namespace = "com.example.scrollsense"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.scrollsense"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"

    }
}

dependencies {
    //    Core AndroidX libraries
    implementation(libs.androidx.core.ktx) // Kotlin extensions for Android core library
    implementation(libs.androidx.appcompat) // Support for older Android versions

    implementation(libs.material)// Material Design components
    implementation(libs.androidx.activity)// Activity library for Jetpack components
    implementation(libs.androidx.constraintlayout)// Layout manager for flexible UI design

    // Testing libraries
    testImplementation(libs.junit)// Unit testing framework
    androidTestImplementation(libs.androidx.junit)// AndroidX JUnit for UI testing
    androidTestImplementation(libs.androidx.espresso.core)// Espresso for UI testing automation

    // Lifecycle components
    implementation(libs.androidx.lifecycle.viewmodel.ktx) // ViewModel support
    implementation(libs.androidx.lifecycle.livedata.ktx) // LiveData support
    implementation(libs.androidx.lifecycle.runtime.ktx) // Lifecycle runtime utilities
    // UI Enhancements
    implementation (libs.shimmer) // Shimmer effect for loading states
    implementation (libs.lottie) // Lottie animations for animated UI elements

    // Firebase dependencies
    implementation(platform(libs.firebase.bom.v3380))  // Firebase BoM (manages versioning)
    implementation(libs.firebase.analytics) // Firebase Analytics for tracking app usage


}