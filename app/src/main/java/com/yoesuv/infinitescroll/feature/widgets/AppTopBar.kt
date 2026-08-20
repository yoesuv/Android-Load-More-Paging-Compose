package com.yoesuv.infinitescroll.feature.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

const val FONT_SIZE = 18

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    canBack: Boolean = true,
    navigateUp: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    if (canBack) {
        TopAppBar(
            modifier = modifier,
            colors =
                TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                ),
            title = {
                Text(
                    text = title,
                    fontSize = FONT_SIZE.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            },
            navigationIcon = {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "back",
                        tint = Color.White,
                    )
                }
            },
        )
    } else {
        TopAppBar(
            modifier = modifier,
            colors =
                TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                ),
            title = {
                Text(
                    text = title,
                    fontSize = FONT_SIZE.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            },
        )
    }
}
