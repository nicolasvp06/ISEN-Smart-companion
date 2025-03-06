plugins {
    id("kotlin-kapt")
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "fr.isen.pissavinvernet.isensmartcompanion"
    compileSdk = 35

    defaultConfig {
        applicationId = "fr.isen.pissavinvernet.isensmartcompanion"
        minSdk = 25
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions { jvmTarget = "11" }
    buildFeatures { compose = true }
}

val coreDeps = listOf(
    libs.androidx.core.ktx,
    libs.androidx.lifecycle.runtime.ktx,
    libs.androidx.activity.compose
)

val composeBom = libs.androidx.compose.bom

val composeUI = listOf(
    libs.androidx.ui,
    libs.androidx.ui.graphics,
    libs.androidx.ui.tooling.preview,
    libs.androidx.material3
)

val navigationExtras = listOf(
    "androidx.compose.material:material-icons-extended:1.5.0",
    "androidx.navigation:navigation-compose:2.8.7"
)

val testDeps = listOf(libs.junit)
val androidTestDeps = listOf(
    libs.androidx.junit,
    libs.androidx.espresso.core,
    libs.androidx.ui.test.junit4
)
val debugDeps = listOf(
    libs.androidx.ui.tooling,
    libs.androidx.ui.test.manifest
)

val networkDeps = listOf(
    "com.google.code.gson:gson:2.10.1",
    "com.squareup.retrofit2:retrofit:2.9.0",
    "com.squareup.retrofit2:converter-gson:2.9.0",
    "com.google.code.gson:gson:2.8.8"
)

val runtimeDeps = listOf(
    "androidx.compose.material3:material3:1.0.0",
    "androidx.compose.runtime:runtime:1.5.0",
    "androidx.compose.runtime:runtime-saveable:1.5.0"
)

val aiAndCoroutines = listOf(
    "com.google.ai.client.generativeai:generativeai:0.4.0",
    "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3",
    "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3"
)

val navAndUIExtras = listOf(
    "androidx.navigation:navigation-compose:2.5.3",
    "androidx.compose.ui:ui:1.3.0",
    "androidx.compose.material3:material3:1.0.0",
    "androidx.compose.material:material-icons-extended:1.3.0",
    "androidx.compose.runtime:runtime-saveable:1.3.0",
    "androidx.navigation:navigation-compose:2.5.0"
)

val bottomNavGroup = listOf(
    "androidx.compose.ui:ui:1.4.0",
    "androidx.compose.material3:material3:1.0.0",
    "androidx.navigation:navigation-compose:2.5.0",
    "androidx.compose.runtime:runtime-saveable:1.4.0",
    "androidx.compose.material3:material3:1.0.0"
)

val extraNetworkGroup = listOf(
    "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0",
    "com.google.code.gson:gson:2.8.8",
    "com.squareup.okhttp3:okhttp:4.10.0",
    "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4",
    "androidx.compose.material3:material3:1.2.0-alpha10"
)

val roomDeps = listOf(
    "androidx.room:room-runtime:2.6.1",
    "androidx.room:room-ktx:2.6.1"
)

val miscDeps = listOf(
    "androidx.work:work-runtime-ktx:2.7.1",
    "androidx.core:core-ktx:1.8.0",
    "androidx.compose.material3:material3:1.0.0",
    "androidx.compose.ui:ui:1.4.0",
    "androidx.preference:preference-ktx:1.1.1"
)

dependencies {
    coreDeps.forEach { implementation(it) }
    implementation(composeBom)
    composeUI.forEach { implementation(it) }
    navigationExtras.forEach { implementation(it) }
    testDeps.forEach { testImplementation(it) }
    androidTestDeps.forEach { androidTestImplementation(it) }
    debugDeps.forEach { debugImplementation(it) }
    networkDeps.forEach { implementation(it) }
    runtimeDeps.forEach { implementation(it) }
    aiAndCoroutines.forEach { implementation(it) }
    navAndUIExtras.forEach { implementation(it) }
    bottomNavGroup.forEach { implementation(it) }
    extraNetworkGroup.forEach { implementation(it) }
    roomDeps.forEach { implementation(it) }
    kapt("androidx.room:room-compiler:2.6.1")
    miscDeps.forEach { implementation(it) }
}
