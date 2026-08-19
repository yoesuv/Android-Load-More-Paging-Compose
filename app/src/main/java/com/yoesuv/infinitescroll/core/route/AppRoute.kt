package com.yoesuv.infinitescroll.core.route

import com.yoesuv.infinitescroll.core.models.PostModel
import kotlinx.serialization.Serializable

sealed class AppRoute {
    @Serializable data object Splash : AppRoute()

    @Serializable data object Home : AppRoute()

    @Serializable data object PagingList : AppRoute()

    @Serializable data object PagingGrid : AppRoute()

    @Serializable data class DetailPost(
        val post: PostModel,
    ) : AppRoute()
}
