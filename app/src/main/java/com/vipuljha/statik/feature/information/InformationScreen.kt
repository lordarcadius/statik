package com.vipuljha.statik.feature.information

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vipuljha.statik.core.domain.model.NavItemModel
import com.vipuljha.statik.feature.information.tabs.AppsTab
import com.vipuljha.statik.feature.information.tabs.BatteryTab
import com.vipuljha.statik.feature.information.tabs.HardwareTab
import com.vipuljha.statik.feature.information.tabs.SoftwareTab
import com.vipuljha.statik.navigation.Route

@Composable
fun InformationScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Column(modifier = modifier.fillMaxSize()) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val selectedIndex =
            infoTabItems.indexOfFirst { it.route::class.qualifiedName == currentRoute }
                .coerceAtLeast(0)

        ScrollableTabRow(selectedTabIndex = selectedIndex) {
            infoTabItems.forEachIndexed { index, item ->
                Tab(
                    selected = currentRoute == item.route::class.qualifiedName,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = false }
                            launchSingleTop = true
                        }
                    },
                    text = { Text(item.label) },
                )
            }
        }
        InfoTabNavGraph(navController)
    }
}

@Composable
private fun InfoTabNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.InfoTabDestination.Hardware,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<Route.InfoTabDestination.Hardware> {
            HardwareTab()
        }
        composable<Route.InfoTabDestination.Software> {
            SoftwareTab()
        }
        composable<Route.InfoTabDestination.Battery> {
            BatteryTab()
        }
        composable<Route.InfoTabDestination.Apps> {
            AppsTab()
        }
    }
}

private val infoTabItems = listOf(
    NavItemModel(Route.InfoTabDestination.Hardware, "Hardware", Icons.Filled.Memory),
    NavItemModel(Route.InfoTabDestination.Software, "Software", Icons.Filled.Storage),
    NavItemModel(Route.InfoTabDestination.Battery, "Battery", Icons.Filled.BatteryFull),
    NavItemModel(Route.InfoTabDestination.Apps, "Apps", Icons.Filled.Android)
)