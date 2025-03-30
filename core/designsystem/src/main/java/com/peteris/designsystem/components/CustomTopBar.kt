package com.peteris.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title: String,
    showBackAction: Boolean = false,
    navigateBack: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = Color.Red.copy(alpha = 0.5f),

        ),
        navigationIcon = {
            if (showBackAction) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            navigateBack()
                        },
                    contentDescription = "Back arrow"
                )
            }
        }
    )
}

@Preview
@Composable
private fun Prevew_TopBar() {
    CustomTopBar(
        title = "My title",
        true
    )
}