package com.ahoy.ahoychargingapp.utils

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch

fun <T> Flow<T>.catchIgnoreCancellation(
    action: suspend FlowCollector<T>.(error: Throwable) -> Unit
) = catch {
    if (it !is CancellationException) {
        action(it)
    }
}
