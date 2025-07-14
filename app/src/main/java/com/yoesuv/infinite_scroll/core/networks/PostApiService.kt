package com.yoesuv.infinite_scroll.core.networks

import com.yoesuv.infinite_scroll.data.Constants.BASE_URL
import com.yoesuv.infinite_scroll.models.PostModel
import io.ktor.client.HttpClient
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow

/**
 * API Service for fetching posts from JSONPlaceholder API
 */
class PostApiService(private val client: HttpClient = AppHttpClient.client) {
    
    companion object {
        private const val POSTS_ENDPOINT = "$BASE_URL/posts"
        
        // Default pagination parameters
        private const val DEFAULT_PAGE = 1
        private const val DEFAULT_LIMIT = 20
    }
    
    /**
     * Fetches a paginated list of posts
     * @param page The page number to fetch (1-based)
     * @param limit The number of items per page
     * @return Flow<NetworkResult<List<PostModel>>> The result wrapped in NetworkResult
     */
    fun getPosts(page: Int = DEFAULT_PAGE, limit: Int = DEFAULT_LIMIT): Flow<NetworkResult<List<PostModel>>> {
        return client.safeRequest {
            get(POSTS_ENDPOINT) {
                parameter("_page", page)
                parameter("_limit", limit)
            }
        }
    }
}
