package com.yoesuv.infinite_scroll.core.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.yoesuv.infinite_scroll.core.networks.PostApiService
import com.yoesuv.infinite_scroll.core.paging.PostPagingSource
import com.yoesuv.infinite_scroll.core.models.PostModel
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of PostRepository that uses PostApiService with Paging 3 library
 */
class PostRepositoryImpl(private val apiService: PostApiService = PostApiService()) :
    PostRepository {

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }

    /**
     * Get paginated posts using Paging 3 library
     * @return Flow<PagingData<PostModel>> A Flow of PagingData containing posts
     */
    override fun getPosts(): Flow<PagingData<PostModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = DEFAULT_PAGE_SIZE * 2
            ),
            pagingSourceFactory = { PostPagingSource(apiService) }
        ).flow
    }
}
