plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.jetbrainsKotlinSerialization)
    alias(libs.plugins.googleDevtoolsKsp)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.firebaseCrashlytics)
    alias(libs.plugins.secretPlugin)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.stefanoq21.socialcleaningcontrol"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.stefanoq21.socialcleaningcontrol"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "0.0.1"

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
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/*"
        }
    }
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
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.adaptive)
    implementation(libs.androidx.material3.adaptive.navigation.suite.android)
    implementation(libs.androidx.compose.animation)
    implementation(libs.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //navigation
    implementation(libs.androidx.navigation.compose)

    //collectAsStateWithLifecycle
    implementation(libs.androidx.lifecycle.runtime.compose)

    //koin
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.android)

    //preference
    implementation(libs.androidx.datastore.preferences)


    // Room
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)

    //Permissions
    implementation(libs.accompanist.permissions)

    //Maps
    implementation(libs.maps.compose)
    implementation(libs.maps.compose.utils)

    //coil images
    implementation(libs.coil.compose)

    //Json serialization kotlin
    implementation(libs.kotlinx.serialization.json)
}