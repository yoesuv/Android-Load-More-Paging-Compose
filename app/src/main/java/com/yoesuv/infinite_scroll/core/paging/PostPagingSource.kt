package com.yoesuv.infinite_scroll.core.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yoesuv.infinite_scroll.core.networks.NetworkResult
import com.yoesuv.infinite_scroll.core.networks.PostApiService
import com.yoesuv.infinite_scroll.models.PostModel
import kotlinx.coroutines.flow.first

/**
 * PagingSource implementation for loading posts from the API
 */
class PostPagingSource(
    private val apiService: PostApiService
) : PagingSource<Int, PostModel>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    /**
     * Load a page of data from the API
     * @param params LoadParams containing information about what to load
     * @return LoadResult containing the loaded data and keys for next/previous pages
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostModel> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            // Get posts from API and collect the first emission (could be Loading or Success)
            val response = apiService.getPosts(page, params.loadSize).first {
                it !is NetworkResult.Loading
            }

            // Handle the response based on its type
            when (response) {
                is NetworkResult.Success -> {
                    val posts = response.data

                    LoadResult.Page(
                        data = posts,
                        prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                        nextKey = if (posts.isEmpty()) null else page + 1
                    )
                }

                is NetworkResult.Error -> {
                    val errorMessage = response.message ?: response.exception?.message ?: "Unknown error"
                    LoadResult.Error(response.exception ?: Exception(errorMessage))
                }

                is NetworkResult.Loading -> LoadResult.Error(Exception("Unexpected loading state"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    /**
     * Get the refresh key for the PagingState
     * @param state Current PagingState
     * @return Key to use for refresh
     */
    override fun getRefreshKey(state: PagingState<Int, PostModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
