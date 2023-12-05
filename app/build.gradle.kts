plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.suku.mvvm.cleanarch"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.suku.mvvm.cleanarch"
        minSdk = 21
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // 2.9.0 ->
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // 2.9.0 ->

    // Interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1") // 4.9.1-> 4.11.0

    //GSON
    implementation("com.google.code.gson:gson:2.9.0") // 2.9.0 ->

    //Glide for image loading
    implementation("com.github.bumptech.glide:glide:4.12.0")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4") // 1.5.2 ->
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4") // 1.5.2 ->

    // Coroutine Lifecycle Scopes
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2") // 2.4.1 ->
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2") // 2.4.1 ->

    // Live Data
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")// 2.4.1 ->

    //dagger=hilt
    implementation("com.google.dagger:hilt-android:2.49")
    kapt("com.google.dagger:hilt-android-compiler:2.49")

    // Activity KTX for viewModels()
    implementation("androidx.activity:activity-ktx:1.8.1") // 1.4.0 ->

    //Room
    implementation("androidx.room:room-runtime:2.4.3")
    kapt("androidx.room:room-compiler:2.4.3")

}

kapt {
    correctErrorTypes = true
}
