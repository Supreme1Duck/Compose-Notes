plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.gms.google-services")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {

    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = "com.example.duck.fastnotes"
        minSdk = 24
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-alpha02"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    //Kotlin
    implementation(Dependencies.core)
    implementation(Dependencies.viewModel)
    implementation(Dependencies.kotlin)

    implementation(Dependencies.appCompat)
    implementation(Dependencies.material)
    implementation(Dependencies.compose)
    implementation(Dependencies.composeMaterial)
    implementation(Dependencies.composePreview)
    implementation(Dependencies.composeConstraint)
    implementation(Dependencies.accompanistCompose)
    implementation(Dependencies.accompanistInsetsCompose)
    implementation(Dependencies.accompanistInsetsUICompose)
    implementation(Dependencies.accompanistFlow)
    implementation(Dependencies.runtimeCompose)
    implementation(Dependencies.liveDataCompose)
    implementation(Dependencies.moshi)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    implementation("androidx.activity:activity-compose:1.4.0")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation(Dependencies.composeTests)
    debugImplementation(Dependencies.composeTooling)
    implementation(Dependencies.navigationCompose)
    implementation(Dependencies.coilCompose)
    implementation(Dependencies.composeHiltNav)

    //Instead Java.Date
    implementation(Dependencies.jodaTime)

    //Hilt
    implementation(Dependencies.hilt)
    kapt(Dependencies.hiltKapt)

    //Kotlin Coroutines
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.coroutinesAndroid)

    //Room
    implementation(Dependencies.roomKtx)
    implementation(Dependencies.roomRuntime)
    kapt(Dependencies.roomCompiler)

    //Navigation
    implementation(Dependencies.navigationKtx)
    implementation(Dependencies.navigationUi)

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:29.0.4"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-dynamic-links-ktx")

    implementation(Dependencies.timber)

    // Retrofit and OkHttp (Not needed for now)
    // OkHttp interceptors for logging (Not needed for now)
    //    implementation ("com.squareup.retrofit2:retrofit:2.8.0")
    //    implementation ("com.squareup.retrofit2:converter-gson:2.8.0")
    //    implementation ("com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0")
    //    implementation ("com.squareup.okhttp3:okhttp:4.4.1")
    //    implementation ("com.squareup.okhttp3:okhttp-urlconnection:3.2.0")
    //    implementation ("com.squareup.okhttp3:logging-interceptor:4.4.1")


}

kapt {
    correctErrorTypes = true
}