plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.kotlin.kapt)
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
//    implementation(libs.mediation.test.suite)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


//    val lifecycle_version = "2.8.7"
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

//    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0"
//    implementation (androidx.lifecycle:lifecycle-livedata-ktx:2.4.0)
//    implementation (androidx.recyclerview:recyclerview:1.2.1)
    implementation (libs.shimmer)
//    implementation("com.google.android.gms:play-services-ads:23.6.0")

//    implementation fd(androidx.recyclerview:recyclerview:1.4.0)
//    // For control over item selection of both touch and mouse driven selection
//    implementation (androidx.recyclerview:recyclerview-selection:1.1.0)
    val lottieVersion = "3.4.0"
    implementation (libs.lottie)

}