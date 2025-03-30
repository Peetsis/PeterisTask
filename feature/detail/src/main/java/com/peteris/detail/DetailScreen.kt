package com.peteris.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.peteris.designsystem.components.CustomTopBar
import com.peteris.designsystem.theme.Typography
import com.peteris.model.Driver

@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    onBackClick: () -> Unit
) {

    val state = viewModel.state.collectAsStateWithLifecycle(initialValue = DetailState()).value

    Scaffold(
        topBar = {
            state.driverData?.name?.let {
                CustomTopBar(
                    title = it,
                    showBackAction = true,
                    navigateBack = onBackClick
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                state.driverData?.let {
                    ProfileImage(url = state.driverData.driverImage)
                    ProfileInfo(it)
                }
            }
        }
    }
}

@Composable
private fun ProfileInfo(driver: Driver) {
    Box(
        modifier = Modifier.padding(horizontal = 12.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.Red.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(4.dp)
                )
                .shadow(
                    elevation = 1.dp,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(12.dp)
        ) {
            InfoRow(label = "Name:", info = driver.name)
            InfoRow(label = "Team name:", info = driver.teamName, logo = driver.teamLogo)
            InfoRow(label = "Position:", info = driver.position.toString())
            InfoRow(label = "Driver number:", info = driver.number.toString())
            InfoRow(label = "Wins:", info = driver.wins.toString())
            InfoRow(label = "Points:", info = driver.points.toString())
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun InfoRow(
    label: String,
    info: String,
    logo: String? = null
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = label,
            style = Typography.titleLarge.copy(
                fontWeight = FontWeight.W600
            )
        )
        Text(
            text = info,
            style = Typography.titleMedium.copy(
                fontWeight = FontWeight.W500
            )
        )
        logo?.let {
            AsyncImage(
                model = it,
                contentDescription = "Logo",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
private fun ProfileImage(url: String?) {
    val imageLoader = rememberAsyncImagePainter(model = url)

    Image(
        contentScale = ContentScale.Fit,
        painter = imageLoader,
        modifier = Modifier
            .size(200.dp)
            .padding(
                start = 4.dp,
                end = 4.dp,
                top = 4.dp
            ),
        contentDescription = "Driver image"
    )
}

@Preview
@Composable
private fun Preview_DetailScreen() {

}

