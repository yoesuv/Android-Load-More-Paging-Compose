package com.yoesuv.infinitescroll.feature.paginglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yoesuv.infinitescroll.core.models.PostModel
import com.yoesuv.infinitescroll.core.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class PagingListViewModel(
    private val postRepository: PostRepository,
) : ViewModel() {
    val posts: Flow<PagingData<PostModel>> =
        postRepository
            .getPosts()
            .cachedIn(viewModelScope)
}
