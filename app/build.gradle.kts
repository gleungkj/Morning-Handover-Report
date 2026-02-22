

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose.compiler)
}

android {
    namespace = "com.example.morning_handover_report"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.morning_handover_report"
        minSdk = 23
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
            // Add this line for your live/production API
            buildConfigField("String", "BASE_URL", "\"https://api.your-production-domain.com\"")
        }

        // Add this entire block for your test/development API
        debug {
            buildConfigField("String", "BASE_URL", "\"https://api.your-development-domain.com\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
        compose = true
        android.buildFeatures.buildConfig = true

    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // --- Add the Compose Dependencies ---    // 1. Import the Compose Bill of Materials (BOM)
    // This manages all the versions for the compose libraries below
    implementation(platform("androidx.compose:compose-bom:2026.02.00"))

    // 2. Core Compose libraries for UI and Material 3 design
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.material3:material3")

    // 3. Integration with Activities
    implementation("androidx.activity:activity-compose:1.9.0")

    // 4. Tooling for Previews and Inspection
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
}