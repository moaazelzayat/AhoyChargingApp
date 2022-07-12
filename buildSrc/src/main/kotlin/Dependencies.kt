import org.gradle.api.Project

/**
 * Interface used to declare a grouping of dependencies and exposing valuable information in
 * groupings.
 */
internal interface AppDependencyList {

    /**
     * [ArrayList] of [String] dependencies that will require the `implementation` configuration
     * name.
     */
    val implementation: ArrayList<String>
}

/**
 * Dependencies is an object used to declare our application-level dependencies. The respective values
 * will be exposed for the [Project] level assignments.
 */
object Dependencies {

    /**
     * List of project level dependencies
     */
    val classpath: ArrayList<String>
        get() = Build.implementation.apply {
            add(Dagger.core)
        }

    /**
     * List of app level dependencies that require the `implementation` configuration name.
     */
    val implementation: ArrayList<String>
        get() = arrayListOf<String>().apply {
            addAll(Accompanist.implementation)
            addAll(AndroidX.implementation)
            addAll(Retrofit.implementation)
            addAll(Compose.implementation)
            addAll(Core.implementation)
            addAll(Dagger.implementation)
            addAll(Ktx.implementation)
            addAll(Networking.implementation)
            addAll(ThirdParty.implementation)
        }

    /**
     * List of app level dependencies that require the `kapt` configuration name.
     */
    val kapt: ArrayList<String>
        get() = arrayListOf(
            Dagger.compiler,
            Dagger.hiltCompiler,
        )

    /**
     * List of app level dependencies that require the `ksp` configuration name.
     */
    val ksp: ArrayList<String>
        get() = arrayListOf(
            AndroidX.roomCompiler,
            Networking.moshiProcessor,
        )

    /**
     * List of app level dependencies that require the `debugImplementation` configuration name.
     */
    val debugImplementation: ArrayList<String>
        get() = arrayListOf(
            Compose.uiTooling,
            Test.Android.composeManifest,
            Test.Android.fragment,
        )

    /**
     * List of app level dependencies that require the `testImplementation` configuration name.
     */
    val testImplementation: ArrayList<String>
        get() = Test.implementation

    /**
     * List of app level dependencies that require the `androidTestImplementation` configuration name.
     */
    val androidTestImplementation: ArrayList<String>
        get() = Test.Android.implementation

    /**
     * List of app level dependencies that require the `kaptAndroidTest` configuration name.
     */
    val kaptAndroidTest: ArrayList<String>
        get() = arrayListOf(
            Dagger.compiler,
            Dagger.hiltCompiler,
        )

    /**
     * [AppDependencyList] object that contains the list of dependencies related to the third-party
     * library `Accompanist`.
     */
    private object Accompanist : AppDependencyList {
        private const val flowLayout = "com.google.accompanist:accompanist-flowlayout:${Versions.Libraries.accompanist}"
        private const val insets = "com.google.accompanist:accompanist-insets-ui:${Versions.Libraries.accompanist}"
        private const val nav = "com.google.accompanist:accompanist-navigation-animation:${Versions.Libraries.accompanist}"
        private const val systemUI = "com.google.accompanist:accompanist-systemuicontroller:${Versions.Libraries.accompanist}"

        override val implementation: ArrayList<String>
            get() = arrayListOf(
                flowLayout, insets, nav, systemUI
            )
    }

    /**
     * [AppDependencyList] object that contains the list of dependencies related to the `androidx`
     * library set.
     */
    private object AndroidX : AppDependencyList {

        const val appCompat = "androidx.appcompat:appcompat:${Versions.Libraries.androidxAppCompat}"
        const val activityCompose = "androidx.activity:activity-compose:${Versions.Libraries.androidxActivity}"
        const val annotation = "androidx.annotation:annotation:${Versions.Libraries.androidxAnnotation}"
        const val browser = "androidx.browser:browser:${Versions.Libraries.browser}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.Libraries.androidxConstraintLayout}"
        const val core = "androidx.core:core-ktx:${Versions.Libraries.androidxCore}"
        const val fragment = "androidx.fragment:fragment-ktx:${Versions.Libraries.androidxFragment}"
        const val lifecycleProcess = "androidx.lifecycle:lifecycle-process:${Versions.Libraries.androidxLifecycle}"
        const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.Libraries.androidxLifecycle}"
        const val navFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.Libraries.nav}"
        const val navUI = "androidx.navigation:navigation-ui-ktx:${Versions.Libraries.nav}"
        const val prefs = "androidx.preference:preference-ktx:${Versions.Libraries.androidxPrefs}"
        const val room = "androidx.room:room-runtime:${Versions.Libraries.room}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.Libraries.room}"
        const val splash = "androidx.core:core-splashscreen:${Versions.Libraries.androidxSplash}"
        const val swipeRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.Libraries.androidxSwipeRefresh}"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Libraries.androidxLifecycle}"

        override val implementation: ArrayList<String>
            get() = arrayListOf(
                appCompat, activityCompose, annotation, browser, constraintLayout, core,
                fragment, lifecycleProcess, livedata, navFragment, navUI, prefs, room,
                splash, swipeRefresh, viewModel
            )
    }

    /**
     * [AppDependencyList] object that contains the list of dependencies related to the third-party
     * library `Apollo`.
     */
    private object Retrofit : AppDependencyList {

        const val core = "com.squareup.retrofit2:retrofit:${Versions.Libraries.retrofit}"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.Libraries.retrofit}"
        const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.Libraries.retrofit}"
        const val scalarsConverter = "com.squareup.retrofit2:converter-scalars:${Versions.Libraries.retrofit}"

        override val implementation: ArrayList<String>
            get() = arrayListOf(
                core, gsonConverter, moshiConverter, scalarsConverter
            )
    }

    /**
     * [AppDependencyList] object that contains the list of dependencies related to the build
     * configuration of the application. Declare gradle plugin configurations here.
     */
    private object Build : AppDependencyList {

        const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.Build.androidGradlePlugin}"
        const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.Libraries.dagger}"
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Build.kotlin}"
        const val navGradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.Libraries.nav}"

        override val implementation: ArrayList<String>
            get() = arrayListOf(
                androidGradlePlugin,
                hiltGradlePlugin, kotlinGradlePlugin, navGradlePlugin
            )
    }

    /**
     * [AppDependencyList] object that contains the list of dependencies related to library sets
     * that pertain specifically to Jetpack Compose.
     */
    private object Compose : AppDependencyList {

        const val animation = "androidx.compose.animation:animation:${Versions.Libraries.compose}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:${Versions.Libraries.composeConstraintLayout}"
        const val foundation = "androidx.compose.foundation:foundation:${Versions.Libraries.compose}"
        const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:${Versions.Libraries.hiltNavCompose}"
        const val mdc = "androidx.compose.material:material:${Versions.Libraries.compose}"
        const val mdcThemeAdapter = "com.google.android.material:compose-theme-adapter:${Versions.Libraries.compose}"
        const val nav = "androidx.navigation:navigation-compose:${Versions.Libraries.nav}"
        const val ui = "androidx.compose.ui:ui:${Versions.Libraries.compose}"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.Libraries.compose}"
        const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.Libraries.compose}"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.Libraries.androidxLifecycle}"

        override val implementation: ArrayList<String>
            get() = arrayListOf(
                animation, constraintLayout, foundation, hiltNavigation, mdc, nav, ui, uiTooling,
                uiToolingPreview, viewModel, mdcThemeAdapter
            )
    }

    /**
     * [AppDependencyList] object that contains the list of dependencies related to the core
     * functionality of our application. ex. jdk, general purpose util, etc.
     */
    object Core : AppDependencyList {

        /**
         * [jdkDesugar] uses a separate method to add to our dependency graph and will not be added to
         * the `implementation` [ArrayList].
         */
        const val jdkDesugar = "com.android.tools:desugar_jdk_libs:${Versions.Libraries.coreLibraryDesugaring}"

        const val inject = "javax.inject:javax.inject:${Versions.Libraries.inject}"

        override val implementation: ArrayList<String>
            get() = arrayListOf(
                inject
            )
    }

    /**
     * [AppDependencyList] object that contains the list of dependencies related to the third-party
     * library `Dagger`.
     */
    private object Dagger : AppDependencyList {

        const val core = "com.google.dagger:dagger:${Versions.Libraries.dagger}"
        const val compiler = "com.google.dagger:dagger-compiler:${Versions.Libraries.dagger}"
        const val hilt = "com.google.dagger:hilt-android:${Versions.Libraries.dagger}"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.Libraries.dagger}"

        override val implementation: ArrayList<String>
            get() = arrayListOf(
                core, hilt
            )
    }

    /**
     * [AppDependencyList] object that contains the list of dependencies related to the core
     * functionality of Kotlin.
     */
    private object Ktx : AppDependencyList {

        const val core = "androidx.core:core-ktx:${Versions.Libraries.androidxCore}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Libraries.coroutines}"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Libraries.coroutines}"

        override val implementation: ArrayList<String>
            get() = arrayListOf(
                core, coroutines, coroutinesAndroid
            )
    }

    /**
     * [AppDependencyList] object that contains the list of dependencies related to core networking
     * functionality.
     */
    private object Networking : AppDependencyList {

        const val coil = "io.coil-kt:coil-compose:${Versions.Libraries.coil}"
        const val coilGif = "io.coil-kt:coil-gif:${Versions.Libraries.coil}"
        const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.Libraries.moshi}"
        const val moshiProcessor = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.Libraries.moshi}"
        const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.Libraries.okhttp}"
        const val okhttpLogger = "com.squareup.okhttp3:logging-interceptor:${Versions.Libraries.okhttp}"

        override val implementation: ArrayList<String>
            get() = arrayListOf(
                coil, coilGif, moshi, okhttp, okhttpLogger
            )
    }

    /**
     * [AppDependencyList] object that contains the list of dependencies related to all other
     * third-party libraries that aren't easily categorized or have enough similar libraries to be
     * contained in their own [AppDependencyList].
     */
    private object ThirdParty : AppDependencyList {

        const val lottie = "com.airbnb.android:lottie-compose:${Versions.Libraries.lottie}"
        const val markwon = "io.noties.markwon:core:${Versions.Libraries.markwon}"

        override val implementation: ArrayList<String>
            get() = arrayListOf(
                lottie, markwon
            )
    }

    // region - Testing

    /**
     * [AppDependencyList] object that contains the list of dependencies related to the testing
     * within our application.
     */
    private object Test : AppDependencyList {

        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Libraries.coroutines}"
        const val junit = "junit:junit:${Versions.Libraries.Test.junit}"
        const val mockk = "io.mockk:mockk:${Versions.Libraries.Test.mockk}"
        const val robolectric = "org.robolectric:robolectric:${Versions.Libraries.Test.robolectric}"
        const val truth = "com.google.truth:truth:${Versions.Libraries.Test.truth}"
        const val turbine = "app.cash.turbine:turbine:${Versions.Libraries.Test.turbine}"

        /**
         * [AppDependencyList] object within [Test] that contains the list of dependencies related
         * specifically to the `androidTest` package.
         */
        object Android : AppDependencyList {

            const val composeJunit = "androidx.compose.ui:ui-test-junit4:${Versions.Libraries.compose}"
            const val composeManifest = "androidx.compose.ui:ui-test-manifest:${Versions.Libraries.compose}"
            const val core = "androidx.test:core:${Versions.Libraries.Test.androidxTestCore}"
            const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.Libraries.Test.espresso}"
            const val fragment = "androidx.fragment:fragment-testing:${Versions.Libraries.androidxFragment}"
            const val hilt = "com.google.dagger:hilt-android-testing:${Versions.Libraries.dagger}"
            const val jUnit = "androidx.test.ext:junit:${Versions.Libraries.Test.androidxJunit}"
            const val mockk = "io.mockk:mockk-android:${Versions.Libraries.Test.mockk}"
            const val nav = "androidx.navigation:navigation-testing:${Versions.Libraries.nav}"

            override val implementation: ArrayList<String>
                get() = arrayListOf(
                    composeJunit, composeManifest, core, espressoCore, fragment, hilt, jUnit,
                    mockk, nav,
                )
        }

        override val implementation: ArrayList<String>
            get() = arrayListOf(
                coroutines, junit, mockk, robolectric, truth, turbine, Android.jUnit, Android.core,
                Android.composeJunit
            )
    }

    // endregion
}
