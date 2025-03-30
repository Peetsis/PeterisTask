plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.serialization)
    alias(libs.plugins.kotlin.compose)
}


android {
    namespace = "com.peteris.detail"
    compileSdk = 35

    defaultConfig {
        minSdk = 29
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {

    implementation(project(":core:model"))
    implementation(project(":core:data"))
    implementation(project(":core:designsystem"))
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.coil.compose.core)
    implementation(libs.coil.compose.network)

    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.runtime)
    implementation(libs.compose.animation)
    implementation(libs.compose.foundation)
    implementation(libs.compose.foundation.layout)
    implementation(libs.compose.material3)
    implementation(libs.compose.material)
    implementation(libs.navigation.compose)
    implementation(libs.koin.android)
    implementation(libs.koin.compat)
    implementation(libs.koin.compose)
    implementation(libs.ktor.android)
    implementation(libs.ktor.core)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.logging)
    implementation(libs.androidx.activity.compose)
    implementation(libs.material)
    testImplementation(libs.junit)
    testImplementation(libs.jupiter)
    testImplementation(libs.jupiter.engine)
    testImplementation(libs.koin.test)
    testImplementation(libs.turbine)
    testImplementation(libs.coroutines.test)
    testRuntimeOnly(libs.jupiter.engine)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}