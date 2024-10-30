plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt") // needed for hilt
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.mongodb.realm.kotlin)

    kotlin("plugin.serialization").version(libs.versions.kotlin)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ktlint)
}

android {
    namespace = "com.anon2024dev2020.tictactoe"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.anon2024dev2020.tictactoe"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        //    Hilt uses Java 8 features. To enable Java 8 in your project:
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // ==================== Core ====================
    // The ktx library for Android provides Kotlin extensions that make Android development
    // more concise, idiomatic, and pleasant by leveraging Kotlin's features.
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // ==================== Dependency Injection ====================
    // Dagger Hilt https://developer.android.com/training/dependency-injection/hilt-android#kts
    // Needs Kapt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)

    kapt(libs.hilt.android.compiler)

    // ==================== Testing ====================
    testImplementation(libs.junit)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // ==================== Compose & UI ====================
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // ==================== Networking ====================
    // TODO: add networking dependencies

    // ==================== Database ====================
    // For a free tier online DB with sync capabilities,
    // add device-sync dependency, see: https://www.mongodb.com/docs/atlas/device-sdks/sdk/kotlin/install/#std-label-kotlin-install-android

    // MongoDB Local-Only Realm SDK
    implementation(libs.mongodb.realm.kotlin.library.base)

    // ==================== CoRoutines ====================
    // Need this to use coroutines with the MongoDB Realm SDK (among other use cases)
    implementation(libs.kotlinx.coroutines)

    // ==================== Navigation (Compose) ====================
    implementation(libs.androidx.navigation.compose)

    // ==================== Serialization ====================
    implementation(
        libs.kotlinx.serialization.json,
    ) // Used (also) for type-safe Navigation, to annotate classes with @Serializable, for example
}

// Needed for hilt
// Allow references to generated code:
kapt {
    correctErrorTypes = true
}

ktlint {
    android.set(true)
    outputColorName.set("RED")
}
