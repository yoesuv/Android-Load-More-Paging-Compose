package com.yoesuv.infinite_scroll.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.yoesuv.infinite_scroll.compose.R
import com.yoesuv.infinite_scroll.core.route.AppRoute
import com.yoesuv.infinite_scroll.core.theme.Grey50
import com.yoesuv.infinite_scroll.feature.widgets.AppTopBar

@Composable
fun HomeScreen(
    navController: NavHostController,
) {
    Scaffold(
        topBar = {
            AppTopBar(title = stringResource(R.string.app_name), canBack = false)
        },
        containerColor = Grey50
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    navController.navigate(AppRoute.PagingList)
                }
            ) {
                Text(
                    text = stringResource(R.string.pagination_list),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    navController.navigate(AppRoute.PagingGrid)
                }
            ) {
                Text(
                    text = stringResource(R.string.pagination_grid),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
