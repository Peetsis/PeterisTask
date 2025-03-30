package com.peteris.list.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.peteris.designsystem.theme.Typography
import com.peteris.designsystem.R

@Composable
internal fun ListErrorScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier.padding(
                horizontal = 32.dp,
                vertical = 64.dp
            )
        ) {
            Image(
                painter = painterResource(R.drawable.ic_error),
                modifier = Modifier.size(100.dp),
                contentDescription = "Error image"
            )
            Text(
                text = "Error occurred",
                style = Typography.titleLarge
            )
        }
    }
}

@Preview
@Composable
private fun Error_Preview() {
    ListErrorScreen()
}