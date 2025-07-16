package com.yoesuv.infinite_scroll.feature.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.yoesuv.infinite_scroll.compose.R
import com.yoesuv.infinite_scroll.core.theme.Grey50
import com.yoesuv.infinite_scroll.core.theme.InfiniteScrollTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit = {}
) {
    // Create a current-state holder that won't change on recomposition
    val currentOnNavigateToHome by rememberUpdatedState(onNavigateToHome)

    // Launch effect to handle the delay and navigation
    LaunchedEffect(key1 = true) {
        delay(2000) // 2 seconds delay
        currentOnNavigateToHome()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Grey50
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                stringResource(R.string.app_name),
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            )
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    InfiniteScrollTheme {
        SplashScreen()
    }
}