package com.yoesuv.infinite_scroll

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yoesuv.infinite_scroll.core.di.ViewModelFactory
import com.yoesuv.infinite_scroll.core.route.AppRoute
import com.yoesuv.infinite_scroll.core.theme.InfiniteScrollTheme
import com.yoesuv.infinite_scroll.feature.home.HomeScreen
import com.yoesuv.infinite_scroll.feature.paging_grid.PagingGridScreen
import com.yoesuv.infinite_scroll.feature.paging_grid.PagingGridViewModel
import com.yoesuv.infinite_scroll.feature.paging_list.PagingListScreen
import com.yoesuv.infinite_scroll.feature.paging_list.PagingListViewModel
import com.yoesuv.infinite_scroll.feature.splash.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InfiniteScrollTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoute.Splash,
    ) {
        composable<AppRoute.Splash> {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(AppRoute.Home) {
                        // Clear the back stack so user can't go back to splash
                        popUpTo(AppRoute.Splash) { inclusive = true }
                    }
                }
            )
        }
        composable<AppRoute.Home> {
            HomeScreen(
                onPaginationListClick = {
                    navController.navigate(AppRoute.PagingList)
                },
                onPaginationGridClick = {
                    navController.navigate(AppRoute.PagingGrid)
                }
            )
        }

        composable<AppRoute.PagingList> {
            val factory = ViewModelFactory.getInstance()
            val viewModel: PagingListViewModel = viewModel(factory = factory)
            PagingListScreen(navHost = navController, viewModel = viewModel)
        }

        composable<AppRoute.PagingGrid> {
            val factory = ViewModelFactory.getInstance()
            val viewModel: PagingGridViewModel = viewModel(factory = factory)
            PagingGridScreen(navHost = navController, viewModel = viewModel)
        }
    }
}