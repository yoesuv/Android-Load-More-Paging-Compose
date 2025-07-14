package com.yoesuv.infinite_scroll.core.networks

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.accept
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import android.util.Log
import com.yoesuv.infinite_scroll.data.Constants
import io.ktor.client.call.body
import io.ktor.client.plugins.observer.ResponseObserver

/**
 * Singleton object that provides a configured Ktor HttpClient instance
 */
object AppHttpClient {
    
    private const val TIME_OUT = 60_000L
    private const val TAG = "AppHttpClient"
    
    /**
     * Creates and returns a configured Ktor HttpClient instance
     */
    val client = HttpClient(Android) {
        // Configure timeout
        install(HttpTimeout) {
            requestTimeoutMillis = TIME_OUT
            connectTimeoutMillis = TIME_OUT
            socketTimeoutMillis = TIME_OUT
        }
        
        // Configure content negotiation with JSON serialization
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        
        // Configure logging for debugging
        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
        }
        
        // Install ResponseObserver
        install(ResponseObserver) {
            onResponse {
                response ->
                val code = response.status.value
                val url = response.request.url
                val headers = response.headers.entries()

                Log.d(TAG, "${Constants.SEPARATOR} BEGIN REQUEST ${Constants.SEPARATOR}")
                Log.d(TAG, "Response from: $url")
                Log.d(TAG, "Status code: $code")
                Log.d(TAG, "Headers: ${headers.joinToString()}")
                Log.d(TAG, "BODY : ${response.body<String>()}")
                Log.d(TAG, "${Constants.SEPARATOR} END REQUEST ${Constants.SEPARATOR}")
            }
        }
        
        // Configure default request headers
        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
        
        // Handle exceptions
        HttpResponseValidator {
            validateResponse { response ->
                val statusCode = response.status.value
                
                when (statusCode) {
                    in 300..399 -> throw RedirectResponseException(response, "Redirect Error: ${response.status}")
                    in 400..499 -> throw ClientRequestException(response, "Client Request Error: ${response.status}")
                    in 500..599 -> throw ServerResponseException(response, "Server Response Error: ${response.status}")
                }
            }
        }
    }
}
