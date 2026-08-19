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
import com.yoesuv.infinitescroll.core.theme.infiniteScrollTheme
import com.yoesuv.infinitescroll.feature.detailpost.detailPostScreen
import com.yoesuv.infinitescroll.feature.home.homeScreen
import com.yoesuv.infinitescroll.feature.paginggrid.PagingGridViewModel
import com.yoesuv.infinitescroll.feature.paginggrid.pagingGridScreen
import com.yoesuv.infinitescroll.feature.paginglist.PagingListViewModel
import com.yoesuv.infinitescroll.feature.paginglist.pagingListScreen
import com.yoesuv.infinitescroll.feature.splash.splashScreen
import com.yoesuv.infinitescroll.utils.CustomNavTypes
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            infiniteScrollTheme(dynamicColor = false) {
                appNavigation()
            }
        }
    }
}

@Composable
fun appNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppRoute.Splash,
    ) {
        composable<AppRoute.Splash> {
            splashScreen(
                onNavigateToHome = {
                    navController.navigate(AppRoute.Home) {
                        // Clear the back stack so user can't go back to splash
                        popUpTo(AppRoute.Splash) { inclusive = true }
                    }
                },
            )
        }
        composable<AppRoute.Home> {
            homeScreen(
                navController = navController,
            )
        }

        composable<AppRoute.PagingList> {
            val factory = ViewModelFactory.getInstance()
            val viewModel: PagingListViewModel = viewModel(factory = factory)
            pagingListScreen(
                navHost = navController,
                viewModel = viewModel,
            )
        }

        composable<AppRoute.PagingGrid> {
            val factory = ViewModelFactory.getInstance()
            val viewModel: PagingGridViewModel = viewModel(factory = factory)
            pagingGridScreen(
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
            detailPostScreen(
                navHost = navController,
                post = route.post,
            )
        }
    }
}
