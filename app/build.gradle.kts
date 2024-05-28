plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    kotlin("kapt")
}

android {
    namespace = "com.example.myeventsmanagmentapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myeventsmanagmentapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "2.0"


        // Replace com.example.android.dagger with your class path.
        testInstrumentationRunner = "com.example.myeventsmanagmentapp.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val token = properties["token"]
        buildConfigField("String", "token", "\"$token\"")

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
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    android.buildFeatures.buildConfig = true

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    //coil
    implementation(libs.coil.compose)

    //room database
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    //hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")


    //firebase

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))

    implementation("com.google.firebase:firebase-analytics")

    implementation("com.google.firebase:firebase-auth-ktx:21.0.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
    implementation("com.google.android.gms:play-services-auth:21.1.1")

    //navigation
    implementation("androidx.navigation:navigation-compose:2.6.0")


    androidTestImplementation("com.google.dagger:hilt-android-testing:2.48")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.48")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.2.1")
    androidTestImplementation("androidx.room:room-testing:2.6.1")

    //ui testing
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.05.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("androidx.navigation:navigation-testing:2.6.0")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")


    //unit test

    // Testing dependencies
    testImplementation ("org.mockito:mockito-core:3.11.2")
    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    // Firebase Authentication
    implementation ("com.google.firebase:firebase-auth:21.0.1")


    // The compose calendar library
    implementation("com.kizitonwose.calendar:compose:2.5.1")
    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:2.0.4")


    implementation("com.patrykandpatrick.vico:compose:2.0.0-alpha.20")
    implementation("com.patrykandpatrick.vico:compose-m3:2.0.0-alpha.20")
    implementation("com.patrykandpatrick.vico:core:2.0.0-alpha.20")

}