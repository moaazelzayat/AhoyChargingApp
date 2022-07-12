plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp") version Versions.Build.ksp
}

android {

    signingConfigs {
        val uploadSigningProps = loadPropertiesFile(
            file("${rootDir}/secrets/upload-key.properties")
        )
        if (uploadSigningProps != null) {
            create("upload") {
                storeFile = file("${rootDir}/secrets/upload-keystore.jks")
                storePassword = uploadSigningProps["storePassword"] as String?
                keyAlias = uploadSigningProps["keyAlias"] as String?
                keyPassword = uploadSigningProps["keyPassword"] as String?
            }
        } else {
            logger.quiet("*** Release signing config unavailable ***")
        }
    }

    defaultConfig {
        applicationId = AppConfig.applicationId
        compileSdk = AppConfig.compileSdk
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        signingConfig = signingConfigs.findByName("upload") ?: signingConfigs.findByName("debug")

        testInstrumentationRunner = AppConfig.testInstrumentationRunner
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
        }

        getByName("release") {
            //debuggable true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile(AppConfig.proguardAndroid),
                AppConfig.proguardConsumerRules
            )
        }
    }

    productFlavors {

        flavorDimensions.add(AppConfig.dimension)

        create("staging") {
            dimension = AppConfig.ProductFlavors.Staging.dimension
            applicationIdSuffix = AppConfig.ProductFlavors.Staging.applicationIdSuffix

            AppConfig.ProductFlavors.Staging.configFields.forEach { field ->
                buildConfigField(field.type, field.name, field.value)
            }
        }
        create("prod") {
            dimension = AppConfig.ProductFlavors.Prod.dimension

            AppConfig.ProductFlavors.Prod.configFields.forEach { field ->
                buildConfigField(field.type, field.name, field.value)
            }
        }
    }

    compileOptions {
        // Sets Java compatibility to Java 8
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

        // Flag to enable support for the new language APIs
        isCoreLibraryDesugaringEnabled = true
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = AppConfig.jvmTarget
        freeCompilerArgs = freeCompilerArgs + AppConfig.freeCompilerArgs
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Libraries.compose
    }

    testOptions {
        packagingOptions {
            jniLibs {
                useLegacyPackaging = true
            }
        }
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }

    packagingOptions {
        resources.excludes.add("META-INF/*")
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    coreLibraryDesugaring(Dependencies.Core.jdkDesugar)
    implementation(Dependencies.implementation)
    kapt(Dependencies.kapt)
    ksp(Dependencies.ksp)
    debugImplementation(Dependencies.debugImplementation)
    testImplementation(Dependencies.testImplementation)
    androidTestImplementation(Dependencies.androidTestImplementation)
    kaptAndroidTest(Dependencies.kaptAndroidTest)
}
