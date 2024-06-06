plugins {
    id("com.android.library")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
}
android {
    namespace = "com.arda.case_ui"
    compileSdk = 34

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    coreApi()
    coreUi()
    caseApi()
    hilt()
    compose()
    coil()
    appCompat()
    implementation(Dependencies.kotlinStdlib)
    kapt (Dependencies.metadataJvm)
    testFullAndroid()
}