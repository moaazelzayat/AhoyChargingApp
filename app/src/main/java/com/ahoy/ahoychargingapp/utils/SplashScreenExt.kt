package com.ahoy.ahoychargingapp.utils

import androidx.core.splashscreen.SplashScreen

/**
 * Configures this SplashScreen with a [SplashScreen.KeepOnScreenCondition] that will return true if
 * until [durationMs] milliseconds have elapsed since this function was called.
 *
 * @param durationMs The amount of time in milliseconds to keep the splash screen visible.
 * @param additional An additional condition that will be evaluated AFTER the specified duration
 * [durationMs] has elapsed.
 */
fun SplashScreen.keepVisibleForDuration(
    durationMs: Long,
    additional: SplashScreen.KeepOnScreenCondition = SplashScreen.KeepOnScreenCondition { false }
) {
    val start = System.currentTimeMillis()
    setKeepOnScreenCondition {
        val elapsed = System.currentTimeMillis() - start
        elapsed < durationMs || additional.shouldKeepOnScreen()
    }
}