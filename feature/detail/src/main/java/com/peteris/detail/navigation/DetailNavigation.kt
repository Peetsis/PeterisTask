package com.peteris.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.peteris.detail.DetailScreen
import com.peteris.detail.DetailViewModel
import com.peteris.model.Driver
import com.peteris.model.network.DriversData
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable data class DetailRoute(val driverId: Int)

fun NavController.navigateToDetail(driverId: Int, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = DetailRoute(driverId)) {
        navOptions()
    }
}

fun NavGraphBuilder.detailSection(
    onBackClick: () -> Unit,
) {
    composable<DetailRoute> { entry ->
        val driverId = entry.toRoute<DetailRoute>().driverId
        val viewModel = koinViewModel<DetailViewModel>(
            parameters = { parametersOf(driverId) }
        )
        DetailScreen(
            viewModel = viewModel,
            onBackClick = onBackClick
        )
    }
}