package com.peteris.list.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.peteris.list.ui.ListScreen
import com.peteris.model.Driver
import com.peteris.model.network.DriversData
import kotlinx.serialization.Serializable

@Serializable data object ListRoute

fun NavGraphBuilder.listSection(
    onDetailClick: (Int) -> Unit
) {
    composable<ListRoute> {
        ListScreen(onDetailClick)
    }
}