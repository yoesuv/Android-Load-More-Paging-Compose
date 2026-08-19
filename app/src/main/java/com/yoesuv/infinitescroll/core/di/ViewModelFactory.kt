package com.yoesuv.infinitescroll.core.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yoesuv.infinitescroll.core.networks.PostApiService
import com.yoesuv.infinitescroll.core.repository.PostRepository
import com.yoesuv.infinitescroll.core.repository.PostRepositoryImpl
import com.yoesuv.infinitescroll.feature.paginggrid.PagingGridViewModel
import com.yoesuv.infinitescroll.feature.paginglist.PagingListViewModel

/**
 * Factory for creating ViewModels with dependencies
 */
class ViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(PagingListViewModel::class.java) -> {
                val apiService = PostApiService()
                val repository: PostRepository = PostRepositoryImpl(apiService)
                PagingListViewModel(repository) as T
            }

            modelClass.isAssignableFrom(PagingGridViewModel::class.java) -> {
                val apiService = PostApiService()
                val repository: PostRepository = PostRepositoryImpl(apiService)
                PagingGridViewModel(repository) as T
            }

            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        }

    companion object {
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory().also { instance = it }
            }
    }
}
