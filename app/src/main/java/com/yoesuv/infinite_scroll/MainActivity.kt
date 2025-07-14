package com.yoesuv.infinite_scroll

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yoesuv.infinite_scroll.core.route.AppRoute
import com.yoesuv.infinite_scroll.core.theme.InfiniteScrollTheme
import com.yoesuv.infinite_scroll.feature.home.HomeScreen
import com.yoesuv.infinite_scroll.feature.splash.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InfiniteScrollTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = AppRoute.Splash,
        modifier = modifier
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
                    // TODO: Navigate to pagination list screen when implemented
                },
                onPaginationGridClick = {
                    // TODO: Navigate to pagination grid screen when implemented
                }
            )
        }
        
        // Add more routes as needed
    }
}