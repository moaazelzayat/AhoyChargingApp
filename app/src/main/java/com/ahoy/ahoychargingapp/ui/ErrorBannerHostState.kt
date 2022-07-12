package com.ahoy.ahoychargingapp.ui

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.runtime.*
import com.ahoy.ahoychargingapp.R

/**
 * State for the Error banner host passed via CompositionLocals to children composables
 */
class ErrorBannerHostState {
    var current by mutableStateOf<ErrorBannerState?>(null)

    data class ErrorBannerState(
        val title: String?,
        val message: String?,
        @DrawableRes val icon: Int,
        val onDismiss: (() -> Unit)?
    )

    enum class AnimationState { START, END }

    fun show(
        exception: Throwable,
        context: Context,
        @DrawableRes icon: Int = R.drawable.ic_baseline_error,
        onDismiss: (() -> Unit)? = null,
    ) {

        val exceptionTitle = "Error"
        val exceptionMessage = "There is an error"
        val showError = true

        if (showError) {
            show(
                title = exceptionTitle,
                message = exceptionMessage,
                icon = icon,
                onDismiss = onDismiss
            )
        }
    }

    fun show(
        title: String,
        message: String,
        @DrawableRes icon: Int = R.drawable.ic_baseline_error,
        onDismiss: (() -> Unit)? = null,
    ) {
        current = ErrorBannerState(
            title = title,
            message = message,
            icon = icon,
            onDismiss = onDismiss,
        )
    }

    fun dismiss() {
        current = null
    }
}

@Composable
fun rememberErrorBannerHostState(): ErrorBannerHostState = remember { ErrorBannerHostState() }

val LocalErrorBannerHost = compositionLocalOf<ErrorBannerHostState> {
    error("LocalErrorBannerHost not set")
}