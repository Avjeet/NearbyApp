plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.express.nearbyappassgn"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.express.nearbyappassgn"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug{
            buildConfigField("String", "NEARBY_API_BASE", "\"https://api.seatgeek.com/2\"")
            buildConfigField("String", "NEARBY_CLIENT_ID", "\"Mzg0OTc0Njl8MTcwMDgxMTg5NC44MDk2NjY5\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "NEARBY_API_BASE", "\"https://api.seatgeek.com/2\"")
            buildConfigField("String", "NEARBY_CLIENT_ID", "\"Mzg0OTc0Njl8MTcwMDgxMTg5NC44MDk2NjY5\"")
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
        dataBinding = true
    }
}

dependencies {

    /** Android */
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.extensions)


    /** ArchComponents */
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    /** UI */
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.constraintLayout)
    implementation(libs.material)

    /** Networking */
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.annotations)

    /** Hilt */
    implementation(libs.hilt.android)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.play.services.location)
    ksp(libs.hilt.compiler)



    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}