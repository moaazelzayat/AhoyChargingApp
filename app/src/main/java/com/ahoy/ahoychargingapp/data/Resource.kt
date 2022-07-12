package com.ahoy.ahoychargingapp.data

import android.util.Log
import com.ahoy.ahoychargingapp.utils.catchIgnoreCancellation
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

sealed class Resource<T> {

    abstract val data: T?

    data class Success<T>(
        override val data: T
    ) : Resource<T>()

    data class Loading<T>(
        override val data: T? = null
    ) : Resource<T>()

    data class Error<T>(
        override val data: T? = null,
        val cause: Throwable
    ) : Resource<T>()
}

/**
 * Create a cold [Flow] that executes the [resource] block and emits the status as [Resource]
 * values.  The flow being cold means that the [resource] block is called every time a terminal
 * operator is applied to the resulting flow.  This operator automatically applies the
 * [catchErrorsAsResource] operator.
 *
 * @param initialData The initial data provided in the loading resource, or in the error resource
 * if an exception is thrown.
 * @see catchErrorsAsResource
 */
fun <T> resourceFlow(initialData: T? = null, resource: suspend () -> T) = flow {
    emit(Resource.Loading(initialData))
    emit(Resource.Success(resource()))
}.catchErrorsAsResource(initialData)

/**
 * Catches any upstream exceptions (except [CancellationException]) and emits them as a
 * [Resource.Error].
 */
fun <T> Flow<Resource<T>>.catchErrorsAsResource(
    data: T? = null
) = catchIgnoreCancellation {
    emit(Resource.Error(data = data, cause = it))
}

/**
 * Delays emissions of [Resource.Loading] by a duration of [loadingWindow].  If a [Resource.Success]
 * or [Resource.Error] is emitted within the given window, the loading resource will not be emitted
 * downstream.  If the loading window expires before another item is emitted, any items that come
 * after will be delayed until at least a duration of [minLoadTime] has passed since the loading
 * resource was emitted.
 */
@OptIn(FlowPreview::class)
fun <T> Flow<Resource<T>>.windowedLoadDebounce(
    loadingWindow: Long = 200L,
    minLoadTime: Long = 1000L,
): Flow<Resource<T>> = debounce {
    when (it) {
        is Resource.Loading -> loadingWindow
        else -> 0L
    }
}.map {
    it to System.currentTimeMillis()
}.runningReduce { previous, current ->
    if (previous.first is Resource.Loading) {
        val timeLoadingActual = System.currentTimeMillis() - previous.second
        val delay = (minLoadTime - timeLoadingActual).coerceAtLeast(0L)
        delay(delay)
    }
    current
}.map {
    it.first
}
