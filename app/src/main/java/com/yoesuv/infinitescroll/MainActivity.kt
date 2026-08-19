package com.yoesuv.infinitescroll

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.yoesuv.infinitescroll.core.di.ViewModelFactory
import com.yoesuv.infinitescroll.core.models.PostModel
import com.yoesuv.infinitescroll.core.route.AppRoute
import com.yoesuv.infinitescroll.core.theme.InfiniteScrollTheme
import com.yoesuv.infinitescroll.feature.detailpost.DetailPostScreen
import com.yoesuv.infinitescroll.feature.home.HomeScreen
import com.yoesuv.infinitescroll.feature.paginggrid.PagingGridScreen
import com.yoesuv.infinitescroll.feature.paginggrid.PagingGridViewModel
import com.yoesuv.infinitescroll.feature.paginglist.PagingListScreen
import com.yoesuv.infinitescroll.feature.paginglist.PagingListViewModel
import com.yoesuv.infinitescroll.feature.splash.SplashScreen
import com.yoesuv.infinitescroll.utils.CustomNavTypes
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InfiniteScrollTheme(dynamicColor = false) {
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
                },
            )
        }
        composable<AppRoute.Home> {
            HomeScreen(
                navController = navController,
            )
        }

        composable<AppRoute.PagingList> {
            val factory = ViewModelFactory.getInstance()
            val viewModel: PagingListViewModel = viewModel(factory = factory)
            PagingListScreen(
                navHost = navController,
                viewModel = viewModel,
            )
        }

        composable<AppRoute.PagingGrid> {
            val factory = ViewModelFactory.getInstance()
            val viewModel: PagingGridViewModel = viewModel(factory = factory)
            PagingGridScreen(
                navHost = navController,
                viewModel = viewModel,
            )
        }

        composable<AppRoute.DetailPost>(
            typeMap =
                mapOf(
                    typeOf<PostModel>() to CustomNavTypes.PostModelType,
                ),
        ) { backStackEntry ->
            val handle = backStackEntry.savedStateHandle
            val route =
                handle.toRoute<AppRoute.DetailPost>(
                    typeMap =
                        mapOf(
                            typeOf<PostModel>() to CustomNavTypes.PostModelType,
                        ),
                )
            DetailPostScreen(
                navHost = navController,
                post = route.post,
            )
        }
    }
}
