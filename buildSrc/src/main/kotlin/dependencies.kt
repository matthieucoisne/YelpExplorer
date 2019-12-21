import org.gradle.api.JavaVersion

object Config {
    val compileSdk                      = 29
    val targetSdk                       = 29
    val minSdk                          = 21
    val buildTools                      = "29.0.2"
}

object Versions {
    val gradle_plugin_android           = "4.0.0-alpha06"
    val gradle_plugin_google_services   = "4.3.3"
    val gradle_plugin_fabric            = "1.31.2"

    val java                            = JavaVersion.VERSION_1_8
    val kotlin                          = "1.3.61"
    val kotlin_coroutines               = "1.3.2"

    val android_material                = "1.2.0-alpha01"
    val androidx                        = "1.0.0"
    val androidx_activity               = "1.1.0-rc02"
    val androidx_appCompat              = "1.1.0"
    val androidx_constraintLayout       = "1.1.3"
    val androidx_core                   = "1.2.0-beta02"
    val androidx_fragment               = "1.2.0-rc02"
    val androidx_lifecycle              = "2.2.0-rc02"
    val androidx_lifecycle_savedState   = "1.0.0-rc02"
    val androidx_navigation             = "2.2.0-rc02"
    val androidx_recyclerview           = "1.1.0-rc01"
    val androidx_room                   = "2.2.1"
    val annotation                      = "1.1.0"
    val crashlytics                     = "2.10.1"
    val dagger                          = "2.25.2"
    val glide                           = "4.10.0"
    val graphql                         = "1.2.2"
    val gson                            = "2.8.6"
    val leakcanary                      = "1.6.3"
    val okio                            = "2.2.2"
    val okhttp                          = "4.2.2"
    val retrofit                        = "2.6.2"
    val timber                          = "4.7.1"

    // TESTING
    val androidx_test                   = "1.3.0-alpha02"
    val androidx_core_testing           = "2.1.0"
    val espresso                        = "3.3.0-alpha02"
    val hamcrest                        = "1.3"
    val junit                           = "4.12"
    val mockito                         = "2.25.0"
    val robolectric                     = "4.3.1"
    val assertj                         = "1.7.1" // Do not update. It is the last version that supports android. (see assertj for android - square)
}

object Dependencies {
    // KOTLIN
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    val kotlin_coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlin_coroutines}"
    val kotlin_coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_coroutines}"

    // ANDROIDX
    val androidx_activity_ktx = "androidx.activity:activity-ktx:${Versions.androidx_activity}"
    val androidx_appcompat = "androidx.appcompat:appcompat:${Versions.androidx_appCompat}"
    val androidx_constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.androidx_constraintLayout}"
    val androidx_core_ktx = "androidx.core:core-ktx:${Versions.androidx_core}"
    val androidx_fragment = "androidx.fragment:fragment:${Versions.androidx_fragment}"
    val androidx_legacy_core_utils = "androidx.legacy:legacy-support-core-utils:${Versions.androidx}"
    val androidx_legacy_core_ui = "androidx.legacy:legacy-support-core-ui:${Versions.androidx}"
    val androidx_lifecycle_extentions = "androidx.lifecycle:lifecycle-extensions:${Versions.androidx_lifecycle}"
    val androidx_lifecycle_runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidx_lifecycle}"
    val androidx_lifecycle_viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidx_lifecycle}"
    val androidx_lifecycle_viewmodel_savedstate = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.androidx_lifecycle_savedState}"
    val androidx_lifecycle_livedata_core_ktx = "androidx.lifecycle:lifecycle-livedata-core-ktx:${Versions.androidx_lifecycle}"
    val androidx_lifecycle_livedata_ktx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.androidx_lifecycle}"
    val androidx_lifecycle_common_java8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.androidx_lifecycle}"
    val androidx_navigation_runtime_ktx = "androidx.navigation:navigation-runtime-ktx:${Versions.androidx_navigation}"
    val androidx_navigation_fragment_ktx = "androidx.navigation:navigation-fragment-ktx:${Versions.androidx_navigation}"
    val androidx_navigation_ui_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.androidx_navigation}"
    val androidx_recyclerview = "androidx.recyclerview:recyclerview:${Versions.androidx_recyclerview}"

    // MATERIAL
    val android_material = "com.google.android.material:material:${Versions.android_material}"

    // ROOM
    val androidx_room_runtime = "androidx.room:room-runtime:${Versions.androidx_room}"
    val androidx_room_ktx = "androidx.room:room-ktx:${Versions.androidx_room}"
    val androidx_room_compiler = "androidx.room:room-compiler:${Versions.androidx_room}"
    val androidx_room_testing = "androidx.room:room-testing:${Versions.androidx_room}"

    // DAGGER
    val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    val dagger_android = "com.google.dagger:dagger-android:${Versions.dagger}"
    val dagger_android_processor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    val dagger_android_support = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    val dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"

    // GRAPHQL
    val graphql = "com.apollographql.apollo:apollo-runtime:${Versions.graphql}"
    val graphql_coroutines = "com.apollographql.apollo:apollo-coroutines-support:${Versions.graphql}"

    // GSON
    val gson = "com.google.code.gson:gson:${Versions.gson}"

    // OKIO
    val okio = "com.squareup.okio:okio:${Versions.okio}"

    // OKHTTP
    val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    val okhttp_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

    // RETROFIT
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofit_converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val retrofit_mock = "com.squareup.retrofit2:retrofit-mock:${Versions.retrofit}"

    // GLIDE
    val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    val glide_okhttp_integration = "com.github.bumptech.glide:okhttp3-integration:${Versions.glide}"

    // TIMBER
    val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    // CRASHLYTICS
    val crashlytics = "com.crashlytics.sdk.android:crashlytics:${Versions.crashlytics}"

    // LEAK CANARY
    val leakcanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakcanary}"


    // UNIT TESTS
    val junit = "junit:junit:${Versions.junit}"
    val assertj = "org.assertj:assertj-core:${Versions.assertj}"
    val hamcrest = "org.hamcrest:hamcrest-library:${Versions.hamcrest}"
    val androidx_arch_core_testing = "androidx.arch.core:core-testing:${Versions.androidx_core_testing}"
    val kotlinx_coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlin_coroutines}"

    val mockito_core = "org.mockito:mockito-core:${Versions.mockito}"
    //http://static.javadoc.io/org.mockito/mockito-core/2.22.0/org/mockito/Mockito.html#0.2
    val mockito_inline = "org.mockito:mockito-inline:${Versions.mockito}"


    val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"

    // INSTRUMENTATION TESTS
    val androidx_annotation = "androidx.annotation:annotation:${Versions.annotation}"
    val androidx_test_runner = "androidx.test:runner:${Versions.androidx_test}"
    val androidx_test_rules = "androidx.test:rules:${Versions.androidx_test}"
    val androidx_test_espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    val androidx_test_espresso_idling_resource = "androidx.test.espresso:espresso-idling-resource:${Versions.espresso}"
    val androidx_test_espresso_contrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
}

object GradlePlugins {
    val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val fabric = "io.fabric.tools:gradle:${Versions.gradle_plugin_fabric}"
    val android = "com.android.tools.build:gradle:${Versions.gradle_plugin_android}"
    val androidx_navigation_safe_args = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.androidx_navigation}"
    val google_services =  "com.google.gms:google-services:${Versions.gradle_plugin_google_services}"
    val graphql = "com.apollographql.apollo:apollo-gradle-plugin:${Versions.graphql}"
}
