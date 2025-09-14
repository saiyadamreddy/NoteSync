plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    id("com.google.devtools.ksp") // Apply the KSP plugin
}

android {
    namespace = "com.app.notesfeature"
    compileSdk = 36

    defaultConfig {
        minSdk = 21
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
}

dependencies {

    // Android Core
    implementation(libs.androidx.core.ktx)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Activity
    implementation(libs.androidx.activity.compose)

    // Compose (Foundation & UI)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    // Compose Material
    implementation(libs.compose.material.icons.extended)
    implementation(libs.androidx.material3)

    // In your module-level build.gradle.kts
    implementation("androidx.navigation:navigation-compose:2.7.7") // Use the latest version

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Room Database
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler) // KSP for Room annotation processing

    // Retrofit + OkHttp
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.gson)

    // Google Auth
    implementation(libs.google.auth)

    // Core testing
    testImplementation(libs.junit) // Or JUnit 5 equivalent
    testImplementation(libs.test.junit) // Or your Kotlin version
    // MockK for mocking
    testImplementation(libs.mockk)
    testImplementation(libs.mockk.android) // if you need to mock Android specific classes, not strictly needed here
    // Coroutines testing
    testImplementation(libs.kotlinx.coroutines.test) // Use the latest version


}