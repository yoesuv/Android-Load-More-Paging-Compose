package com.yoesuv.infinite_scroll.core.repository

import com.yoesuv.infinite_scroll.core.networks.NetworkResult
import com.yoesuv.infinite_scroll.models.PostModel
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for post-related data operations
 * This interface makes testing easier by allowing mock implementations
 */
interface PostRepository {
    /**
     * Get paginated posts
     * @param page The page number to fetch
     * @param limit The number of items per page
     * @return Flow<NetworkResult<List<PostModel>>> The result wrapped in NetworkResult
     */
    fun getPosts(page: Int, limit: Int): Flow<NetworkResult<List<PostModel>>>
}
