package com.yoesuv.infinitescroll.core.networks

/**
 * Sealed class to handle different types of network responses
 * @param T The type of data expected in the response
 */
sealed class NetworkResult<out T> {
    /**
     * Loading state
     */
    object Loading : NetworkResult<Nothing>()

    /**
     * Successful response with data
     */
    data class Success<T>(
        val data: T,
    ) : NetworkResult<T>()

    /**
     * Error state with optional error message and exception
     */
    data class Error(
        val message: String? = null,
        val exception: Exception? = null,
    ) : NetworkResult<Nothing>()

    companion object {
        /**
         * Helper function to create a Success result
         */
        fun <T> success(data: T): NetworkResult<T> = Success(data)

        /**
         * Helper function to create an Error result with message
         */
        fun <T> error(message: String): NetworkResult<T> = Error(message = message)

        /**
         * Helper function to create an Error result with exception
         */
        fun <T> error(exception: Exception): NetworkResult<T> = Error(exception = exception)

        /**
         * Helper function to create an Error result with both message and exception
         */
        fun <T> error(
            message: String,
            exception: Exception,
        ): NetworkResult<T> = Error(message = message, exception = exception)

        /**
         * Helper function to create a Loading result
         */
        fun <T> loading(): NetworkResult<T> = Loading
    }
}
