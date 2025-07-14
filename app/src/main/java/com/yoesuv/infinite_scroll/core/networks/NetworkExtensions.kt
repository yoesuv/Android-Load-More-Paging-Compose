package com.yoesuv.infinite_scroll.core.networks

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
            try {
                val body: T = response.body()
                emit(NetworkResult.success(body))
            } catch (e: Exception) {
                emit(NetworkResult.parseError("Failed to parse response: ${e.message}"))
            }
        } else {
            // Handle HTTP error
            emit(NetworkResult.httpError(response.status.value, response.status.description))
        }
    } catch (e: RedirectResponseException) {
        // 3xx responses
        emit(NetworkResult.httpError(e.response.status.value, "Redirect error: ${e.message}"))
    } catch (e: ClientRequestException) {
        // 4xx responses
        emit(NetworkResult.httpError(e.response.status.value, "Client request error: ${e.message}"))
    } catch (e: ServerResponseException) {
        // 5xx responses
        emit(
            NetworkResult.httpError(
                e.response.status.value,
                "Server response error: ${e.message}"
            )
        )
    } catch (e: IOException) {
        // Network errors
        emit(NetworkResult.networkError("Network error: ${e.message}"))
    } catch (e: Exception) {
        // Generic errors
        emit(NetworkResult.genericError(e))
    }
}
