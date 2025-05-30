plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.course_add_and_drop_manager_app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.course_add_and_drop_manager_app"
        minSdk = 22
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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    packaging {
        resources {
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/io.netty.versions.properties"
            excludes += "META-INF/INDEX.LIST"
            excludes += "mozilla/public-suffix-list.txt"
        }
    }
}

dependencies {
    // Core AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // ✅ Compose BOM for version alignment
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))

    // Compose UI
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3") // ✅ Only one material3 version
    implementation("androidx.compose.material:material-icons-extended")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    // Firebase (if actually used)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.firebase.appdistribution.gradle)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // Debugging
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Networking and Serialization
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")

    // Lifecycle and Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
}
