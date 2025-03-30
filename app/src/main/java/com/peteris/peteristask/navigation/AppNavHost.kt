package com.peteris.peteristask.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.peteris.detail.navigation.detailSection
import com.peteris.detail.navigation.navigateToDetail
import com.peteris.list.navigation.ListRoute
import com.peteris.list.navigation.listSection

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ListRoute
    ) {
        listSection(onDetailClick = navController::navigateToDetail)
        detailSection(onBackClick = navController::popBackStack)
    }
}