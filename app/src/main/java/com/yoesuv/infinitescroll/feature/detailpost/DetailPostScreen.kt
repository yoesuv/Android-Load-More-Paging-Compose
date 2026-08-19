package com.yoesuv.infinitescroll.feature.detailpost

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yoesuv.infinite_scroll.compose.R
import com.yoesuv.infinitescroll.core.models.PostModel
import com.yoesuv.infinitescroll.core.theme.Grey50
import com.yoesuv.infinitescroll.feature.widgets.AppTopBar

@Composable
fun DetailPostScreen(
    navHost: NavHostController,
    post: PostModel,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.detail_post),
                navigateUp = {
                    navHost.navigateUp()
                },
            )
        },
        contentColor = Grey50,
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp),
        ) {
            Text(
                "Id : ${post.id}",
                style =
                    TextStyle(
                        color = Color.Black,
                        fontSize = 14.sp,
                    ),
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Title
            Text(
                text = post.title,
                style =
                    TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    ),
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Body
            Text(
                text = post.body,
                style =
                    TextStyle(
                        fontSize = 14.sp,
                        color = Color.Black,
                    ),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
