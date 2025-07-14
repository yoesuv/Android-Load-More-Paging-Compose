package com.yoesuv.infinite_scroll.core.repository

import com.yoesuv.infinite_scroll.core.networks.NetworkResult
import com.yoesuv.infinite_scroll.core.networks.PostApiService
import com.yoesuv.infinite_scroll.models.PostModel
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of PostRepository that uses PostApiService
 */
class PostRepositoryImpl(private val apiService: PostApiService = PostApiService()) : PostRepository {
    
    /**
     * Get paginated posts from the API service
     */
    override fun getPosts(page: Int, limit: Int): Flow<NetworkResult<List<PostModel>>> {
        return apiService.getPosts(page, limit)
    }
}
