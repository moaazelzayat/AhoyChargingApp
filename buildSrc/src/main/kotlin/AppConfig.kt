/**
 * Application level configuration constants
 */
object AppConfig {

    // region - DefaultConfig

    const val applicationId = "com.ahoy.ahoychargingapp"
    const val compileSdk = 31
    const val minSdk = 24
    const val targetSdk = 31
    const val versionCode = 1
    const val versionName = "2022.07"

    const val testInstrumentationRunner = "com.zenbusiness.app.HiltTestRunner"

    // region - Product Flavors

    /**
     * Product flavor dimension
     */
    const val dimension = "environment"

    data class BuildConfigField(
        val type: String = "String",
        val name: String,
        val value: String
    )

    sealed class ProductFlavors(
        val dimension: String = AppConfig.dimension,
        val applicationIdSuffix: String? = null,
        val configFields: List<BuildConfigField>,
    ) {

        private companion object {
            const val KEY_BASE_URL = "BASE_URL"
            const val KEY_API_KEY = "API_KEY"

            /**
             * Creates convenient list of [BuildConfigField] to be used in our product flavor
             * configuration.
             */
            fun createConfigFields(
                baseUrl: String,
                apiKey: String
            ): List<BuildConfigField> =
                mutableListOf(
                    BuildConfigField(
                        name = KEY_BASE_URL,
                        value = baseUrl.wrappedInQuotes()
                    ),
                    BuildConfigField(
                        name = KEY_API_KEY,
                        value = apiKey.wrappedInQuotes()
                    )
                )
        }

        object Staging : ProductFlavors(
            applicationIdSuffix = ".staging",
            configFields = createConfigFields(
                baseUrl = "https://api.openchargemap.io/v3/",
                apiKey = "5a8d43c3-b839-45bd-a80d-0f7c207d328a"
            ),
        )

        object Prod : ProductFlavors(
            configFields = createConfigFields(
                baseUrl = "https://api.openchargemap.io/v3/",
                apiKey = "5a8d43c3-b839-45bd-a80d-0f7c207d328a"
            ),
        )
    }

    /**
     * Adds escaped quotes to the given [String].
     *
     * @see `VariantDimension.buildConfigField()` for the value in this method.
     */
    private fun String.wrappedInQuotes(): String = "\"$this\""

    // endregion

    // region - Proguard

    const val proguardAndroid = "proguard-android.txt"
    const val proguardConsumerRules = "proguard-rules.pro"

    // endregion

    // region - Kotlin Options

    const val jvmTarget = "1.8"

    /**
     * List of additional Kotlin compiler arguments
     */
    val freeCompilerArgs: List<String>
        get() = listOf(
            // Generate native Java 8 default interface methods.
            "-Xjvm-default=all",
            // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-requires-opt-in/#requiresoptin
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xopt-in=androidx.compose.animation.ExperimentalAnimationApi",
            "-Xopt-in=androidx.compose.foundation.ExperimentalFoundationApi",
            "-Xopt-in=androidx.compose.ui.ExperimentalComposeUiApi",
            "-Xopt-in=androidx.compose.material.ExperimentalMaterialApi",
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        )

    // endregion
}

// endregion
