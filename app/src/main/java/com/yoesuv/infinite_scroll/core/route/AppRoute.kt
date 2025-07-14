package com.yoesuv.infinite_scroll.core.route

import kotlinx.serialization.Serializable

sealed class AppRoute {
    @Serializable data object Splash : AppRoute()
    @Serializable data object Home : AppRoute()
    @Serializable data object PagingList : AppRoute()
    @Serializable data object PagingGrid : AppRoute()
}