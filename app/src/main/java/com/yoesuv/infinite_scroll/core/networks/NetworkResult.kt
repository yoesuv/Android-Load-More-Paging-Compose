package com.yoesuv.infinite_scroll.core.networks

/**
 * Sealed class to handle different types of network responses
 * @param T The type of data expected in the response
 */
sealed class NetworkResult<out T> {
    /**
     * Successful response with data
     */
    data class Success<T>(val data: T) : NetworkResult<T>()
    
    /**
     * HTTP error with status code and message
     */
    data class HttpError<T>(val code: Int, val message: String) : NetworkResult<T>()
    
    /**
     * Error parsing the response
     */
    data class ParseError<T>(val error: String) : NetworkResult<T>()
    
    /**
     * Network connectivity error
     */
    data class NetworkError<T>(val error: String) : NetworkResult<T>()
    
    /**
     * Generic exception
     */
    data class GenericError<T>(val error: Exception) : NetworkResult<T>()
    
    /**
     * Loading state
     */
    class Loading<T> : NetworkResult<T>()
    
    companion object {
        /**
         * Helper function to create a Success result
         */
        fun <T> success(data: T): NetworkResult<T> = Success(data)
        
        /**
         * Helper function to create an HttpError result
         */
        fun <T> httpError(code: Int, message: String): NetworkResult<T> = HttpError(code, message)
        
        /**
         * Helper function to create a ParseError result
         */
        fun <T> parseError(error: String): NetworkResult<T> = ParseError(error)
        
        /**
         * Helper function to create a NetworkError result
         */
        fun <T> networkError(error: String): NetworkResult<T> = NetworkError(error)
        
        /**
         * Helper function to create a GenericError result
         */
        fun <T> genericError(error: Exception): NetworkResult<T> = GenericError(error)
        
        /**
         * Helper function to create a Loading result
         */
        fun <T> loading(): NetworkResult<T> = Loading()
    }
}
