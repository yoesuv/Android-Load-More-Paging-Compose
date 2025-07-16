package com.yoesuv.infinite_scroll.feature.paging_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.yoesuv.infinite_scroll.compose.R
import com.yoesuv.infinite_scroll.core.route.AppRoute
import com.yoesuv.infinite_scroll.core.theme.Grey50
import com.yoesuv.infinite_scroll.feature.widgets.AppTopBar

@Composable
fun PagingListScreen(navHost: NavHostController, viewModel: PagingListViewModel) {
    val pagingItems = viewModel.posts.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            AppTopBar(title = stringResource(R.string.pagination_list), navigateUp = {
                navHost.navigateUp()
            })
        },
        containerColor = Grey50
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues = innerPadding)
                .fillMaxSize()
        ) {
            items(pagingItems.itemCount) { index ->
                val post = pagingItems[index]
                ItemPost(post = post, modifier = Modifier.clickable {
                    post?.let {
                        navHost.navigate(AppRoute.DetailPost(it))
                    }
                })
                HorizontalDivider(color = Color.LightGray)
            }

            // Handle initial loading state
            when (pagingItems.loadState.refresh) {
                is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) { CircularProgressIndicator(modifier = Modifier.size(32.dp)) }
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text(
                            "Error loading posts",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                else -> {}
            }

            // Handle append loading state
            when (pagingItems.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(32.dp)
                            )
                        }
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text(
                            "Error loading more posts",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                else -> {}
            }
        }
    }
}