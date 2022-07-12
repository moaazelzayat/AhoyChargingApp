import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.initialization.dsl.ScriptHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope

private const val KAPT = "kapt"
private const val KSP = "ksp"
private const val IMPLEMENTATION = "implementation"
private const val DEBUG_IMPLEMENTATION = "debugImplementation"
private const val TEST_IMPLEMENTATION = "testImplementation"
private const val ANDROID_TEST_IMPLEMENTATION = "androidTestImplementation"
private const val KAPT_ANDROID_TEST = "kaptAndroidTest"

/**
 * Convenience method for adding `classpath` dependencies to the [DependencyHandlerScope].
 *
 * @param list [List] of [String] dependencies to be added to the [DependencyHandlerScope]
 */
fun DependencyHandler.classpath(list: List<String>) = list.forEach { dependency ->
    add(ScriptHandler.CLASSPATH_CONFIGURATION, dependency)
}

/**
 * Convenience method for adding `kapt` dependencies to the [DependencyHandlerScope].
 *
 * @param list [List] of [String] dependencies to be added to the [DependencyHandlerScope]
 */
fun DependencyHandler.kapt(list: List<String>) = list.forEach { dependency ->
    add(KAPT, dependency)
}

/**
 * Convenience method for adding `ksp` dependencies to the [DependencyHandlerScope].
 *
 * @param list [List] of [String] dependencies to be added to the [DependencyHandlerScope]
 */
fun DependencyHandler.ksp(list: List<String>) = list.forEach { dependency ->
    add(KSP, dependency)
}

/**
 * Convenience method for adding `implementation` dependencies to the [DependencyHandlerScope].
 *
 * @param list [List] of [String] dependencies to be added to the [DependencyHandlerScope]
 */
fun DependencyHandler.implementation(list: List<String>) = list.forEach { dependency ->
    add(IMPLEMENTATION, dependency)
}

/**
 * Convenience method for adding `implementation` dependencies that require a `platform` notation
 * to the [DependencyHandlerScope].
 *
 * @param list [List] of [String] dependencies to be added to the [DependencyHandlerScope]
 */
fun DependencyHandler.platformList(list: List<String>) = list.forEach { dependency ->
    add(IMPLEMENTATION, platform(dependency))
}

/**
 * Convenience method for declaring `debugImplementation` dependencies to
 * the [DependencyHandlerScope].
 *
 * @param list [List] of [String] dependencies to be added to the [DependencyHandlerScope]
 */
fun DependencyHandler.debugImplementation(list: List<String>) = list.forEach { dependency ->
    add(DEBUG_IMPLEMENTATION, dependency)
}

/**
 * Convenience method for adding `testImplementation` dependencies to the [DependencyHandlerScope].
 *
 * @param list [List] of [String] dependencies to be added to the [DependencyHandlerScope]
 */
fun DependencyHandler.testImplementation(list: List<String>) = list.forEach { dependency ->
    add(TEST_IMPLEMENTATION, dependency)
}

/**
 * Convenience method for adding `androidTestImplementation` dependencies to
 * the [DependencyHandlerScope].
 *
 * @param list [List] of [String] dependencies to be added to the [DependencyHandlerScope]
 */
fun DependencyHandler.androidTestImplementation(list: List<String>) = list.forEach { dependency ->
    add(ANDROID_TEST_IMPLEMENTATION, dependency)
}

/**
 * Convenience method for adding `kaptAndroidTest` dependencies to the [DependencyHandlerScope].
 *
 * @param list [List] of [String] dependencies to be added to the [DependencyHandlerScope]
 */
fun DependencyHandler.kaptAndroidTest(list: List<String>) = list.forEach { dependency ->
    add(KAPT_ANDROID_TEST, dependency)
}
