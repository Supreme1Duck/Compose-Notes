object BuildPlugin {

    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}" }
    val android by lazy { "com.android.tools.build:gradle:${Versions.gradleVersion}"}
}

object Dependencies {

    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"

    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"

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
    const val composeMaterial = "androidx.compose.material:material:${Versions.composeVersion}"
    const val composePreview = "androidx.compose.ui:ui-tooling-preview:${Versions.composeVersion}"
    const val composeTests = "androidx.compose.ui:ui-test-junit4:${Versions.composeVersion}"
    const val composeTooling = "androidx.compose.ui:ui-tooling:${Versions.composeVersion}"


}