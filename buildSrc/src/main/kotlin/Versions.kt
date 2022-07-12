/**
 * Object used to declare all version numbers related to our dependencies
 */
object Versions {

    /**
     * Grouped object that declares versions specifically related to our plugin information and
     * all project level dependencies.
     *
     * i.e. kotlin, plugins, etc.
     */
    object Build {
        const val androidGradlePlugin = "7.2.1"
        const val googleServices = "4.3.10"
        const val kotlin = "1.6.10"
        const val ksp = "1.6.10-1.0.4"
    }

    /**
     * Grouped object that declares versions related to all other libraries (at the moment...)
     */
    object Libraries {

        const val accompanist = "0.23.1"
        const val androidxActivity = "1.4.0"
        const val androidxAnnotation = "1.3.0"
        const val androidxAppCompat = "1.4.1"
        const val androidxConstraintLayout = "2.1.2"
        const val androidxCore = "1.7.0"
        const val androidxFragment = "1.4.1"
        const val androidxLifecycle = "2.4.1"
        const val androidxPrefs = "1.1.1"
        const val androidxSplash = "1.0.0-rc01"
        const val androidxSwipeRefresh = "1.1.0"
        const val retrofit = "2.9.0"
        const val browser = "1.4.0"
        const val coil = "1.4.0"
        const val compose = "1.1.1"
        const val composeConstraintLayout = "1.0.0"
        const val coreLibraryDesugaring = "1.1.5"
        const val coroutines = "1.6.0"
        const val dagger = "2.42"
        const val hiltNavCompose = "1.0.0"
        const val inject = "1"
        const val lottie = "4.2.2"
        const val markwon = "4.6.2"
        const val moshi = "1.13.0"
        const val nav = "2.4.2"
        const val okhttp = "4.9.1"
        const val room = "2.4.0"

        object Test {
            const val androidxJunit = "1.1.3"
            const val androidxTestCore = "1.4.0"
            const val espresso = "3.4.0"
            const val junit = "4.13.2"
            const val mockk = "1.12.4"
            const val robolectric = "4.6.1"
            const val truth = "1.1.3"
            const val turbine = "0.7.0"
        }
    }
}
