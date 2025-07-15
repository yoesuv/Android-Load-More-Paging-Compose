package com.yoesuv.infinite_scroll.feature.paging_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoesuv.infinite_scroll.models.PostModel

@Composable
fun ItemPost(post: PostModel?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = post?.title ?: "",
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.Black)
        )
        Text(
            text = post?.body ?: "",
            style = TextStyle(fontSize = 12.sp, color = Color.Black.copy(alpha = 0.8F))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemPostPreview() {
    ItemPost(
        post = PostModel(
            userId = 1,
            id = 1,
            title = "title",
            body = "lorem ipsum dolor sit amet"
        )
    )
}