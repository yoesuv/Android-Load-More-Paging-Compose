package com.yoesuv.infinite_scroll.feature.paging_grid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yoesuv.infinite_scroll.core.repository.PostRepository
import com.yoesuv.infinite_scroll.core.models.PostModel
import kotlinx.coroutines.flow.Flow

class PagingGridViewModel(
    private val postRepository: PostRepository
) : ViewModel() {

    val posts: Flow<PagingData<PostModel>> = postRepository.getPosts()
        .cachedIn(viewModelScope)

}