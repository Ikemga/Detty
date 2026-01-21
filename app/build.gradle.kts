plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("plugin.serialization") version "2.0.21"

}

android {
    namespace = "ifaq.ujkz.detty"
    compileSdk = 36

    defaultConfig {
        applicationId = "ifaq.ujkz.detty"
        minSdk = 29
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

    /**
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.10.0-RC")
    implementation("io.github.jan-tennert.supabase:postgrest-kt:2.2.1")


    implementation("io.ktor:ktor-client-cio:2.3.3")
     *
     *  implementation("io.github.jan-tennert.supabase:auth-kt:2.2.1")
     *  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.10.0-RC")
     * **/


    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

}