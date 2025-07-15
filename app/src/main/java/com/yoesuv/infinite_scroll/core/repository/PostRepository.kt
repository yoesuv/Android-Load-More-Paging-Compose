package com.yoesuv.infinite_scroll.core.repository

import androidx.paging.PagingData
import com.yoesuv.infinite_scroll.core.models.PostModel
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for post-related data operations
 * This interface makes testing easier by allowing mock implementations
 */
interface PostRepository {
    /**
     * Get paginated posts using Paging 3 library
     * @return Flow<PagingData<PostModel>> A Flow of PagingData containing posts
     */
    fun getPosts(): Flow<PagingData<PostModel>>
}
