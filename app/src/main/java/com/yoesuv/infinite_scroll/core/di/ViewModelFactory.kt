package com.yoesuv.infinite_scroll.core.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yoesuv.infinite_scroll.core.networks.PostApiService
import com.yoesuv.infinite_scroll.core.repository.PostRepository
import com.yoesuv.infinite_scroll.core.repository.PostRepositoryImpl
import com.yoesuv.infinite_scroll.feature.paging_list.PagingListViewModel

/**
 * Factory for creating ViewModels with dependencies
 */
class ViewModelFactory : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PagingListViewModel::class.java) -> {
                val apiService = PostApiService()
                val repository: PostRepository = PostRepositoryImpl(apiService)
                PagingListViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
    
    companion object {
        private var instance: ViewModelFactory? = null
        
        fun getInstance(): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory().also { instance = it }
            }
        }
    }
}
