object Deps {
    // COMPOSE
    private const val activityComposeVersion = "1.7.2"
    const val activityCompose = "androidx.activity:activity-compose:$activityComposeVersion"

    const val composeVersion = "1.5.3"
    const val composeUi = "androidx.compose.ui:ui:$composeVersion"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:$composeVersion"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
    const val composeFoundation = "androidx.compose.foundation:foundation:$composeVersion"
    const val composeMaterial = "androidx.compose.material:material:$composeVersion"
    const val composeIconsExtended =
        "androidx.compose.material:material-icons-extended:$composeVersion"

    private const val composeNavigationVersion = "2.5.3"
    private const val composeNavigationAnimVersion = "0.31.0-alpha"
    const val composeNavigation = "androidx.navigation:navigation-compose:$composeNavigationVersion"
    const val composeNavigationAnim =
        "com.google.accompanist:accompanist-navigation-animation:$composeNavigationAnimVersion"

    private const val coilComposeVersion = "2.1.0"
    const val coilCompose = "io.coil-kt:coil-compose:$coilComposeVersion"

    private const val lifecycleVersion = "2.6.1"
    const val composeLifecycle = "androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion"

    // KOTLIN DATE TIME
    private const val dateTimeVersion = "0.4.0"
    const val kotlinDateTime = "org.jetbrains.kotlinx:kotlinx-datetime:$dateTimeVersion"


    ///ANDROID RELATED
    //REVIEW
    private const val reviewVersion = "2.0.1"
    const val playReview = "com.google.android.play:review:$reviewVersion"
    const val playReviewKtx = "com.google.android.play:review-ktx:$reviewVersion"

    const val apcompat = "androidx.appcompat:appcompat:1.6.1"
    const val coreKtx = "androidx.core:core-ktx:1.12.0"

    //LOTTIE
    private const val lottieVersion = "6.0.0"
    const val lottie = "com.airbnb.android:lottie-compose:$lottieVersion"

    //IMAGE CROPPER
    private const val cropperVersion = "4.5.0"
    const val imageCropper = "com.vanniktech:android-image-cropper:$cropperVersion"

    //WORK MANAGER
    private const val workManagerVersion = "2.8.1"
    const val workManager = "androidx.work:work-runtime-ktx:$workManagerVersion"

    //BILLING ANDROID
    private const val billingVersion = "5.1.0"
    const val billingClient = "com.android.billingclient:billing:$billingVersion"
    const val billingClientKtx = "com.android.billingclient:billing-ktx:$billingVersion"

    //GUAVA
    const val guavaJreVersion = "com.google.guava:guava:24.1-jre"
    const val guavaAndroidVersion = "com.google.guava:guava:27.0.1-android"

    //PERMISSIONS
    private const val permissionsVersion = "0.31.0-alpha"
    const val permissions = "com.google.accompanist:accompanist-permissions:$permissionsVersion"

    private const val kammelVersion = "0.7.1"
    const val kammel = "media.kamel:kamel-image:$kammelVersion"

    //CHARTS
    private const val chartVersion = "Beta-0.0.5"
    const val chart = "io.github.thechance101:chart:$chartVersion"

    // KTOR
    private const val ktorVersion = "2.3.2"
    const val ktorCore = "io.ktor:ktor-client-core:$ktorVersion"
    const val ktorSerialization = "io.ktor:ktor-client-content-negotiation:$ktorVersion"
    const val ktorSerializationJson = "io.ktor:ktor-serialization-kotlinx-json:$ktorVersion"
    const val ktorAndroid = "io.ktor:ktor-client-android:$ktorVersion"
    const val ktorIOS = "io.ktor:ktor-client-ios:$ktorVersion"

    // GRADLE PLUGINS
    const val kotlinVersion = "1.9.10"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"

    private const val gradleVersion = "7.2.2"
    const val androidBuildTools = "com.android.tools.build:gradle:$gradleVersion"

    private const val sqlDelightGradleVersion = "1.5.5"
    const val sqlDelightGradlePlugin =
        "com.squareup.sqldelight:gradle-plugin:$sqlDelightGradleVersion"


    private const val crashlyticsGradleVer = "2.9.4"
    const val crashlyticsGradlePlugin =
        "com.google.firebase:firebase-crashlytics-gradle:$crashlyticsGradleVer"

    // SQLDELIGHT
    private const val sqlDelightVersion = "1.5.5"
    const val sqlDelightRuntime = "com.squareup.sqldelight:runtime:$sqlDelightVersion"
    const val sqlDelightAndroidDriver = "com.squareup.sqldelight:android-driver:$sqlDelightVersion"
    const val sqlDelightNativeDriver = "com.squareup.sqldelight:native-driver:$sqlDelightVersion"
    const val sqlDelightCoroutinesExtensions =
        "com.squareup.sqldelight:coroutines-extensions:$sqlDelightVersion"

    // FIREBASE
    private const val firebaseVersion = "1.8.1"
    const val firebaseAuth = "dev.gitlive:firebase-auth:$firebaseVersion"
    const val firebaseFirestore = "dev.gitlive:firebase-firestore:$firebaseVersion"
    const val firebaseRemoteConfig = "dev.gitlive:firebase-config:$firebaseVersion"

    //GOOGLE-SERVICES
    private const val servicesVersion = "4.3.15"
    const val googleServices = "com.google.gms:google-services:$servicesVersion"
    private const val bomVersion = "31.2.0"
    const val bom = "com.google.firebase:firebase-bom:$bomVersion"
    const val firebaseAuthAndroid = "com.google.firebase:firebase-auth-ktx"
    const val firebaseStorageAndroid = "com.google.firebase:firebase-storage-ktx"
    const val firebaseStorageAndroidMain = "com.google.firebase:firebase-storage-ktx:20.1.0"
    const val firebaseCrashlyticsKtx = "com.google.firebase:firebase-crashlytics-ktx"
    const val firebaseAnalyticsKtx = "com.google.firebase:firebase-crashlytics-ktx"
    const val firebaseMessagingKtx = "com.google.firebase:firebase-messaging-ktx"

    const val androidXCore = "androidx.core:core:1.12.0"

    //PRECOMPOSE for navigation
    private const val precomposeVersion = "1.5.0"
    const val precompose = "moe.tlaster:precompose:$precomposeVersion"

    //MOKO MVVM
    private const val mokoMvvmVersion = "0.16.1"
    const val mokoMvvmCore = "dev.icerock.moko:mvvm-core:$mokoMvvmVersion"
    const val mokoMvvmCompose = "dev.icerock.moko:mvvm-compose:$mokoMvvmVersion"
    const val mokoMvvmFlow = "dev.icerock.moko:mvvm-flow:$mokoMvvmVersion"
    const val mokoMvvmFlowCompose = "dev.icerock.moko:mvvm-flow-compose:$mokoMvvmVersion"

    //MOKO RESOURCES SHARE
    private const val mokoResVersion = "0.23.0"
    const val mokoRes = "dev.icerock.moko:resources-generator:$mokoResVersion"
    const val mokoSharedRes = "dev.icerock.moko:resources:$mokoResVersion"
    const val mokoSharedResCompose = "dev.icerock.moko:resources-compose:$mokoResVersion"
    private const val mokoIosGraphicsVersion = "0.9.0"
    const val mokoSharedGraphics = "dev.icerock.moko:graphics:$mokoIosGraphicsVersion"

    private const val odysseyVersion = "1.3.20"
    const val odysseyCore = "io.github.alexgladkov:odyssey-core:$odysseyVersion"
    const val odysseyCompose = "io.github.alexgladkov:odyssey-compose:$odysseyVersion"

    // TESTING
    private const val assertKVersion = "0.25"
    const val assertK = "com.willowtreeapps.assertk:assertk:$assertKVersion"

    private const val turbineVersion = "0.7.0"
    const val turbine = "app.cash.turbine:turbine:$turbineVersion"

    private const val jUnitVersion = "4.13.2"
    const val jUnit = "junit:junit:$jUnitVersion"

    private const val testRunnerVersion = "1.5.1"
    const val testRunner = "androidx.test:runner:$testRunnerVersion"

    const val composeTesting = "androidx.compose.ui:ui-test-junit4:$composeVersion"
    const val composeTestManifest = "androidx.compose.ui:ui-test-manifest:$composeVersion"

}