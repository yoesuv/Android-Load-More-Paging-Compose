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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yoesuv.infinite_scroll.compose.R
import com.yoesuv.infinite_scroll.feature.widgets.AppTopBar

@Composable
fun HomeScreen(
    onPaginationListClick: () -> Unit = {},
    onPaginationGridClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            AppTopBar(title = stringResource(R.string.app_name), canBack = false)
        }
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
                onClick = onPaginationListClick
            ) {
                Text(text = stringResource(R.string.pagination_list))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onPaginationGridClick
            ) {
                Text(text = stringResource(R.string.pagination_grid))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}
