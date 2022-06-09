plugins {
    id("com.android.library")
    id("kotlin-parcelize")
    kotlin("android")
}

android {
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "BASE_URL", AppConfig.BASE_URL)
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
    api(Dependencies.stdLibraries)
    api(Dependencies.androidUILibraries)
    api(Dependencies.koinLibraries)
    api(Dependencies.archLibraries)
    api(Dependencies.navLibraries)
    api(Dependencies.reactiveLibraries)
    api(Dependencies.glide)
    api(Dependencies.timber)
    api(Dependencies.lottie)

    implementation(Dependencies.networkLibraries)
    implementation(Dependencies.dbLibraries)

    kapt(Dependencies.kaptLibraries)

    implementation(Dependencies.testLibraries)
    androidTestImplementation(Dependencies.androidTestLibraries)
}