plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "1.8.10"
    id("com.apollographql.apollo3") version "3.8.2"
}

android {
    namespace = "com.yelpexplorer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.yelpexplorer"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.yelpexplorer.runner.MockTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // ANDROIDX
    implementation(platform("androidx.compose:compose-bom:${Versions.compose_bom}"))
    implementation("androidx.compose.runtime:runtime")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.lifecycle:lifecycle-runtime-compose")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle_viewmodel_compose}")
    implementation("androidx.activity:activity-compose:${Versions.activity_compose}")
    implementation("androidx.navigation:navigation-compose:${Versions.androidx_navigation}")

    // JSON
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinx_serialization_json}")

    // OKIO
    implementation("com.squareup.okio:okio:${Versions.okio}")

    // OKHTTP
    implementation(platform("com.squareup.okhttp3:okhttp-bom:${Versions.okhttp}"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    // RETROFIT
    implementation("com.squareup.retrofit2:retrofit:${Versions.retrofit}")
    implementation("com.squareup.retrofit2:retrofit-mock:${Versions.retrofit}")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")

    // GRAPHQL
    implementation("com.apollographql.apollo3:apollo-runtime:${Versions.graphql}")

    // TIMBER
    implementation("com.jakewharton.timber:timber:${Versions.timber}")

    // LEAK CANARY
//    implementation("com.squareup.leakcanary:leakcanary-android:${Versions.leakcanary}")

    // COIL
    implementation("io.coil-kt:coil-compose:${Versions.coil}")

    // KOIN
    implementation("io.insert-koin:koin-core:${Versions.koin}")
    implementation("io.insert-koin:koin-android:${Versions.koin}")
    implementation("io.insert-koin:koin-androidx-compose:${Versions.koin}")
    implementation("io.insert-koin:koin-androidx-navigation:${Versions.koin}")

    // DEBUG
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // TESTS
    testImplementation("junit:junit:${Versions.junit}")
    testImplementation("androidx.arch.core:core-testing:${Versions.androidx_core_testing}")
    testImplementation("org.mockito.kotlin:mockito-kotlin:${Versions.mockito_kotlin}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlin_coroutines}")
    testImplementation("com.google.truth:truth:${Versions.truth}")
    testImplementation("app.cash.turbine:turbine:${Versions.turbine}")
    testImplementation("com.apollographql.apollo3:apollo-testing-support:${Versions.graphql}")
    testImplementation("com.apollographql.apollo3:apollo-mockserver:${Versions.graphql}")

    androidTestImplementation(platform("androidx.compose:compose-bom:${Versions.compose_bom}"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("androidx.test.ext:junit")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.espresso}")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:${Versions.espresso}")
    androidTestImplementation("androidx.navigation:navigation-testing:${Versions.androidx_navigation}")
    androidTestImplementation("io.insert-koin:koin-test:${Versions.koin}")
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:${Versions.okhttp}")
    androidTestImplementation("org.hamcrest:hamcrest:${Versions.hamcrest}") // needed to run compose UI tests
}

object Versions {
    val compose_bom                     = "2023.09.01"

    val kotlin_coroutines               = "1.7.3"
    val kotlinx_serialization_json      = "1.5.1"

    val activity_compose                = "1.7.2"
    val androidx_navigation             = "2.7.3"
    val lifecycle_viewmodel_compose     = "2.6.2"

    val coil                            = "2.4.0"
    val graphql                         = "3.8.2"
    val koin                            = "3.5.0"
    val leakcanary                      = "2.4"
    val okio                            = "3.5.0"
    val okhttp                          = "4.11.0"
    val retrofit                        = "2.9.0"
    val timber                          = "4.7.1"

    // TESTING
    val androidx_core_testing           = "2.2.0"
    val espresso                        = "3.5.1"
    val hamcrest                        = "2.2"
    val junit                           = "4.13.2"
    val mockito_kotlin                  = "5.1.0"
    val truth                           = "1.1.4"
    val turbine                         = "1.0.0"
}

apollo {
    service("service") {
        packageName.set("com.yelpexplorer")
        // FIXME: https://community.apollographql.com/t/android-warning-duplicate-content-roots-detected-after-just-adding-apollo3-kotlin-client/4529/6
        outputDirConnection {
            connectToKotlinSourceSet("main") // main is by default but setting this explicitly fixed the warning.
        }
    }
}
