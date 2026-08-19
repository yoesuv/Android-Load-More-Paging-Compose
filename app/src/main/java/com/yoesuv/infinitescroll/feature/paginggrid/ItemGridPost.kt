package com.yoesuv.infinitescroll.feature.paginggrid

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoesuv.infinitescroll.core.models.PostModel

@Composable
fun ItemGridPost(
    post: PostModel?,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .aspectRatio(1f)
                .padding(2.dp)
                .border(width = 1.dp, color = Color.LightGray)
                .background(Color.White)
                .padding(12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = post?.title ?: "",
            style =
                TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                ),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemGridPostPreview() {
    ItemGridPost(
        post =
            PostModel(
                userId = 1,
                id = 1,
                title = "Grid Item Title",
                body = "This body text won't be displayed in grid view",
            ),
    )
}
