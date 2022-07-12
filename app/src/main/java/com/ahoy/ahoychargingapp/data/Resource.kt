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
 * Maps a provided [Flow] to [Resource] values by first emitting a [Resource.Loading] resource.
 * The [catchErrorsAsResource] operator is automatically applied.
 *
 * @param initialData The initial data provided in the loading resource, or in the error resource
 * if an exception is thrown.
 * @see catchErrorsAsResource
 */
fun <T> Flow<T>.toResourceFlow(initialData: T? = null): Flow<Resource<T>> =
    map<T, Resource<T>> { Resource.Success(it) }
        .onStart { emit(Resource.Loading(initialData)) }
        .catchErrorsAsResource(initialData)

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
 * For debugging purposes - Convenience function to log each item emitted by this resource flow.
 */
inline fun <reified T> Flow<Resource<T>>.logEach(
    tag: String,
    resourceId: String = T::class.simpleName ?: "Unknown",
) = onEach {
    when (it) {
        is Resource.Error -> Log.e(tag, "~!~ Error [$resourceId] data = ${it.data}", it.cause)
        is Resource.Loading -> Log.d(tag, "--> Loading [$resourceId] data = ${it.data}")
        is Resource.Success -> Log.d(tag, "<-- Success [$resourceId] data = ${it.data}")
    }
}.onCompletion {
    val error = it.takeUnless { it is CancellationException }
    Log.d(tag, "=== Completed [$resourceId] error = $error")
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

/**
 * Returns the first resource that is not [Resource.Loading] as a [Result]
 */
suspend fun <T> Flow<Resource<T>>.toResult(): Result<T> {
    val result = catchErrorsAsResource().first {
        it !is Resource.Loading
    }
    return when (result) {
        is Resource.Loading -> throw IllegalStateException("Received Loading state in toResult")
        is Resource.Success -> Result.success(result.data)
        is Resource.Error -> Result.failure(result.cause)
    }
}
