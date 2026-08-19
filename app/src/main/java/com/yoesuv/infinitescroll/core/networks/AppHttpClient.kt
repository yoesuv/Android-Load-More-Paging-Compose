package com.yoesuv.infinitescroll.core.networks

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Singleton object that provides a configured Ktor HttpClient instance
 */
object AppHttpClient {
    private const val TIME_OUT = 60_000L

    /**
     * Creates and returns a configured Ktor HttpClient instance
     */
    val client =
        HttpClient(Android) {
            // Configure timeout
            install(HttpTimeout) {
                requestTimeoutMillis = TIME_OUT
                connectTimeoutMillis = TIME_OUT
                socketTimeoutMillis = TIME_OUT
            }

            // Configure content negotiation with JSON serialization
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    },
                )
            }

            // Configure logging for debugging
            install(Logging) {
                logger =
                    object : Logger {
                        override fun log(message: String) {
                            Log.d("Ktor", message)
                        }
                    }
                level = LogLevel.ALL
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
                        in 300..399 -> throw RedirectResponseException(
                            response,
                            "Redirect Error: ${response.status}",
                        )

                        in 400..499 -> throw ClientRequestException(
                            response,
                            "Client Request Error: ${response.status}",
                        )

                        in 500..599 -> throw ServerResponseException(
                            response,
                            "Server Response Error: ${response.status}",
                        )
                    }
                }
            }
        }
}
