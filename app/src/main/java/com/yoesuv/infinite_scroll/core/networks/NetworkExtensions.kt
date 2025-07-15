package com.yoesuv.infinite_scroll.core.networks

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch
import kotlinx.serialization.SerializationException
import java.io.IOException

/**
 * Extension function to safely execute a network request and return the result as a Flow of NetworkResult
 */
inline fun <reified T> HttpClient.safeRequest(
    crossinline block: suspend HttpClient.() -> HttpResponse
): Flow<NetworkResult<T>> = flow {
    try {
        // Emit loading state
        emit(NetworkResult.loading())

        // Execute the request
        val response = block()

        // Check if the response is successful
        if (response.status.value in 200..299) {
            // Parse the response body
            val body: T = response.body()
            emit(NetworkResult.success(body))
        } else {
            // Handle HTTP error
            emit(NetworkResult.error("HTTP Error ${response.status.value}: ${response.status.description}"))
        }
    } catch (e: Exception) {
        throw e
    }
}.catch { e ->
    // Handle exceptions outside the main flow to avoid transparency violations
    when (e) {
        is RedirectResponseException -> {
            // 3xx responses
            emit(NetworkResult.error("Redirect error: ${e.message}"))
        }

        is ClientRequestException -> {
            // 4xx responses
            emit(NetworkResult.error("Client request error: ${e.message}"))
        }

        is ServerResponseException -> {
            // 5xx responses
            emit(NetworkResult.error("Server response error: ${e.message}"))
        }

        is IOException -> {
            // Network error
            emit(NetworkResult.error("Network error: ${e.message}"))
        }

        else -> {
            if (e is SerializationException || e.message?.contains("Failed to parse") == true) {
                emit(NetworkResult.error("Failed to parse response: ${e.message}"))
            } else {
                // Generic errors
                emit(NetworkResult.error(Exception("Generic error: ${e.message}")))
            }
        }
    }
}
