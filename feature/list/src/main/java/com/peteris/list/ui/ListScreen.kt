package com.peteris.list.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.peteris.designsystem.components.CustomTopBar
import com.peteris.designsystem.theme.Typography
import com.peteris.list.ListViewModel
import com.peteris.list.state.ListAction
import com.peteris.list.state.ListState
import com.peteris.list.state.RankingSeason
import com.peteris.model.Driver
import org.koin.androidx.compose.koinViewModel
import com.peteris.designsystem.R as DesignResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    onDetailClick: (Int) -> Unit
) {
    val viewModel: ListViewModel = koinViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle(initialValue = ListState()).value
    val refreshState = rememberPullToRefreshState()

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "F1 Driver list"
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .pullToRefresh(
                    isRefreshing = state.isRefreshing,
                    state = refreshState,
                    onRefresh = { viewModel.onAction(ListAction.Refresh) }
                )
        ) {
            AnimatedContent(
                state.isLoading,
                modifier = Modifier.fillMaxSize()
            ) { isLoading ->
                when {
                    isLoading -> ListLoadingComponent()
                    state.error -> ListErrorScreen()
                    state.itemList.isEmpty() -> ListEmptyScreen()
                    else -> ListComponent(
                        state = state,
                        onDetailClick = onDetailClick,
                        onAction = viewModel::onAction
                    )
                }
            }
            PullToRefreshDefaults.Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = state.isRefreshing,
                state = refreshState,
            )
        }
    }
}

@Composable
private fun ListComponent(
    state: ListState,
    onDetailClick: (Int) -> Unit,
    onAction: (ListAction) -> Unit
) {
    val listState = rememberLazyGridState()
    val showDialog = remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .animateContentSize()
            .padding(12.dp)
    ) {
        AnimatedVisibility(
            visible = !state.isOnline
        ) {
            NoNetworkBanner {
                onAction(ListAction.Refresh)
            }
        }
        SeasonComponent(
            modifier = Modifier.fillMaxWidth(),
            currentSeason = state.rankingSeason,
            isOnline = state.isOnline,
            showDialog = showDialog,
            showSeasonDialog = { showDialog.value = true },
            onAction = { onAction(it) }
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            state = listState
        ) {
            items(state.itemList) {
                DetailItem(
                    item = it,
                    onDetailClick = { onDetailClick(it.id) }
                )
            }
        }
    }
}

@Composable
private fun SeasonsDropdown(
    modifier: Modifier,
    expanded: MutableState<Boolean>,
    onChangeSeason: (RankingSeason) -> Unit,
) {
    DropdownMenu(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            RankingSeason.entries.forEach {
                Text(
                    text = it.season.toString(),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        )
                        .clickable { onChangeSeason(it) },
                    style = Typography.titleLarge.copy(
                        fontWeight = FontWeight.W600
                    )
                )
            }
        }
    }
}

@Composable
private fun SeasonComponent(
    modifier: Modifier,
    isOnline: Boolean,
    currentSeason: RankingSeason,
    showDialog: MutableState<Boolean>,
    showSeasonDialog: () -> Unit,
    onAction: (ListAction) -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp
                )
                .clickable {
                    showSeasonDialog()
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = currentSeason.season.toString(),
                    style = Typography.titleMedium.copy(
                        fontWeight = FontWeight.W600
                    )
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Dropdown arrow"
                )
            }
            SeasonsDropdown(
                modifier = Modifier.align(Alignment.BottomCenter),
                expanded = showDialog,
                onChangeSeason = {
                    showDialog.value = false
                    if (it != currentSeason && isOnline) {
                        onAction(ListAction.ChangeSeason(it))
                    }
                },
            )
        }
    }
}

@Composable
private fun DetailItem(
    item: Driver,
    onDetailClick: () -> Unit
) {

    val imageLoader = rememberAsyncImagePainter(
        model = item.driverImage,
        error = painterResource(DesignResource.drawable.ic_error)
    )

    Box(
        modifier = Modifier
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(4.dp)
            )
            .fillMaxSize()
            .background(Color.Red.copy(alpha = 0.7f), RoundedCornerShape(4.dp))
            .clickable {
                onDetailClick()
            }

    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(White)
            ) {
                Image(
                    contentScale = ContentScale.Fit,
                    painter = imageLoader,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 4.dp,
                            end = 4.dp,
                            top = 4.dp
                        ),
                    contentDescription = "Driver image",
                )
            }

            Column(
                modifier = Modifier
                    .height(100.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = "Name: ${item.name}",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = Typography.bodyMedium.copy(
                        fontWeight = FontWeight.W600
                    )
                )
                Text(
                    text = "Team: ${item.teamName}",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = Typography.bodySmall
                )
                Text(
                    text = "Position: ${item.position}",
                    style = Typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun NoNetworkBanner(
    onRefresh: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "No connection",
                style = Typography.titleMedium
            )
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .clickable {
                        onRefresh()
                    }
            ) {
                Text(
                    text = "Retry",
                    style = Typography.titleMedium.copy(
                        fontWeight = FontWeight.W600,
                        color = Color.Blue
                    )
                )
                Icon(
                    imageVector = Icons.Default.Refresh,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Blue,
                    contentDescription = "Retry"
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun List_Preview() {
    ListComponent(
        state = ListState(
            itemList = listOf(
                Driver(
                    id = 1,
                    name = "John",
                    number = 1,
                    driverImage = "2",
                    teamName = "Team2",
                    teamLogo = "img",
                    position = 1,
                    wins = 12,
                    points = 12.0,
                    season = 2021
                ),
                Driver(
                    id = 2,
                    name = "Alex",
                    number = 2,
                    driverImage = "2",
                    teamName = "Team2 Alex",
                    teamLogo = "img",
                    position = 1,
                    wins = 12,
                    points = 12.0,
                    season = 2021
                )
            )
        ),
        onAction = {},
        onDetailClick = {},
    )
}