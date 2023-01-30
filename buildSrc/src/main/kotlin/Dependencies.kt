object Dependencies {

    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"

    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"
    const val dataStore = "androidx.datastore:datastore-preferences:${Versions.dataStoreVersion}"

    const val roomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"

    const val navigationKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"

    const val material = "com.google.android.material:material:${Versions.materialVersion}"

    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModelVersion}"

    const val core = "androidx.core:core-ktx:${Versions.coreVersion}"

    const val hilt = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
    const val hiltKapt = "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"

    const val compose = "androidx.compose.ui:ui:${Versions.composeVersion}"
    const val composeLifecycle = "androidx.lifecycle:lifecycle-runtime-compose:${Versions.composeLifecycle}"
    const val composeMaterial = "androidx.compose.material:material:${Versions.composeVersion}"
    const val composePreview = "androidx.compose.ui:ui-tooling-preview:${Versions.composeVersion}"
    const val composeTests = "androidx.compose.ui:ui-test-junit4:${Versions.composeVersion}"
    const val composeTooling = "androidx.compose.ui:ui-tooling:${Versions.composeVersion}"
    const val composeConstraint = "androidx.constraintlayout:constraintlayout-compose:${Versions.composeConstraint}"
    const val accompanistCompose = "com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanistVersion}"
    const val accompanistInsetsCompose = "com.google.accompanist:accompanist-insets:${Versions.accompanistInsetsVersion}"
    const val accompanistInsetsUICompose = "com.google.accompanist:accompanist-insets-ui:${Versions.accompanistInsetsVersion}"
    const val accompanistFlow = "com.google.accompanist:accompanist-flowlayout:${Versions.accompanistInsetsVersion}"
    const val accompanistUiController = "com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanistUiControllerVersion}"
    const val navigationCompose = "androidx.navigation:navigation-compose:${Versions.composeNavigation}"
    const val navigationComposeAnimation = "com.google.accompanist:accompanist-navigation-animation:${Versions.navigationComposeAnimation}"
    const val liveDataCompose = "androidx.compose.runtime:runtime-livedata:${Versions.composeRuntime}"
    const val runtimeCompose = "androidx.compose.runtime:runtime:${Versions.composeRuntime}"
    const val coilCompose = "io.coil-kt:coil:${Versions.coilVersion}"
    const val landscapistGlide = "com.github.skydoves:landscapist-glide:${Versions.landscapistVersion}"
    const val landscapistCoil = "com.github.skydoves:landscapist-coil:${Versions.landscapistVersion}"

    const val jodaTime = "joda-time:joda-time:${Versions.jodaTimeVersion}"

    const val moshi = "com.squareup.moshi:moshi:${Versions.moshiVersion}"

    const val composeHiltNav = "androidx.hilt:hilt-navigation-compose:${Versions.composeHiltNavVersion}"
}