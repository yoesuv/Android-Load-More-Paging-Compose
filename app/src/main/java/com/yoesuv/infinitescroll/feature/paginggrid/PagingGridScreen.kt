package com.yoesuv.infinitescroll.feature.paginggrid

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.yoesuv.infinite_scroll.compose.R
import com.yoesuv.infinitescroll.core.route.AppRoute
import com.yoesuv.infinitescroll.core.theme.Grey50
import com.yoesuv.infinitescroll.feature.widgets.AppTopBar

@Composable
fun PagingGridScreen(
    navHost: NavHostController,
    viewModel: PagingGridViewModel,
    modifier: Modifier = Modifier,
) {
    val pagingItems = viewModel.posts.collectAsLazyPagingItems()
    Scaffold(
        modifier = modifier,
        topBar = {
            AppTopBar(title = stringResource(R.string.pagination_grid), navigateUp = {
                navHost.navigateUp()
            })
        },
        containerColor = Grey50,
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(2.dp),
            modifier =
                Modifier
                    .padding(paddingValues = innerPadding)
                    .fillMaxSize()
                    .wrapContentSize(),
        ) {
            items(pagingItems.itemCount) { index ->
                val post = pagingItems[index]
                ItemGridPost(
                    post = post,
                    modifier =
                        Modifier.clickable {
                            post?.let {
                                navHost.navigate(AppRoute.DetailPost(it))
                            }
                        },
                )
            }

            // Handle initial loading state
            when (pagingItems.loadState.refresh) {
                is LoadState.Loading -> {
                    item(span = { GridItemSpan(2) }) {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center,
                        ) { CircularProgressIndicator(modifier = Modifier.size(32.dp)) }
                    }
                }

                is LoadState.Error -> {
                    item(span = { GridItemSpan(2) }) {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                "Error loading posts",
                                color = MaterialTheme.colorScheme.error,
                            )
                        }
                    }
                }

                else -> {}
            }

            // Handle append loading state
            when (pagingItems.loadState.append) {
                is LoadState.Loading -> {
                    item(span = { GridItemSpan(2) }) {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(32.dp),
                            )
                        }
                    }
                }

                is LoadState.Error -> {
                    item(span = { GridItemSpan(2) }) {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                "Error loading more posts",
                                color = MaterialTheme.colorScheme.error,
                            )
                        }
                    }
                }

                else -> {}
            }
        }
    }
}
