plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("com.squareup.sqldelight")
    kotlin("plugin.serialization") version Deps.kotlinVersion
    id("dev.icerock.mobile.multiplatform-resources")
    id("org.jetbrains.compose").version("1.6.0-rc01")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "15.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
            export(Deps.mokoSharedRes)
            export(Deps.mokoSharedGraphics)
        }
        extraSpecAttributes["resources"] =
            "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation(compose.ui)

                implementation(Deps.ktorCore)
                implementation(Deps.ktorSerialization)
                implementation(Deps.ktorSerializationJson)
                implementation(Deps.sqlDelightRuntime)
                implementation(Deps.sqlDelightCoroutinesExtensions)
                implementation(Deps.kotlinDateTime)
                implementation(Deps.firebaseAuth)
                implementation(Deps.firebaseFirestore)
                implementation(Deps.firebaseRemoteConfig)

                api(Deps.mokoSharedRes)
                api(Deps.mokoSharedResCompose)

                api(Deps.precompose)

                implementation(Deps.kammel)

                implementation(Deps.chart)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                api(Deps.activityCompose)
                api(Deps.apcompat)
                api(Deps.coreKtx)

                implementation(Deps.sqlDelightAndroidDriver)
                implementation(Deps.ktorAndroid)
                implementation(Deps.firebaseStorageAndroidMain)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation(Deps.ktorIOS)
                implementation(Deps.sqlDelightNativeDriver)
            }
        }
    }
}

android {
    namespace = "jp.mikhail.pankratov.trainingMate"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

sqldelight {
    database("TrainingDatabase") {
        packageName = "jp.mikhail.pankratov.trainingMate.database"
        sourceFolders = listOf("sqldelight")
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "jp.mikhail.pankratov.trainingMate"
    multiplatformResourcesClassName = "SharedRes"
}

dependencies {
    implementation(Deps.androidXCore)
    commonMainApi(Deps.mokoMvvmCore)
    commonMainApi(Deps.mokoMvvmCompose)
    commonMainApi(Deps.mokoMvvmFlow)
    commonMainApi(Deps.mokoMvvmFlowCompose)
}
