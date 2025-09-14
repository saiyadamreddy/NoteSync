plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    kotlin("kapt")
}

android {
    namespace = "com.app.notesync"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.app.notesync"
        minSdk = 24
        targetSdk = 36
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
    buildFeatures {
        compose = true
    }

    composeOptions { kotlinCompilerExtensionVersion = "1.5.3" }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1,LICENSE.md,LICENSE-notice.md}"
            // You can be more specific if you only want to exclude LICENSE.md
            // excludes += "/META-INF/LICENSE.md"
        }
    }

}

dependencies {
    implementation(project(":notesfeature"))

    // Android Core
    implementation(libs.androidx.core.ktx)
    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    // Activity
    implementation(libs.androidx.activity.compose)
    // Compose (Foundation & UI)
    implementation(platform(libs.androidx.compose.bom))

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Google Auth
    implementation(libs.google.auth)

    // Compose UI Testing
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.09.00")) // Use your Compose BOM version
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling") // For tools like Layout Inspector
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Hilt testing (if you need to provide fake ViewModels)
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.51.1") // Match your Hilt version
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.51.1") // Match your Hilt version

    // MockK for Android tests (if mocking ViewModels or dependencies)
    androidTestImplementation("io.mockk:mockk-android:1.13.12")

    // kotlinx-coroutines-test (if ViewModels use coroutines and you need to control them)
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
}