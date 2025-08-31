package com.vipuljha.statik.feature.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.vipuljha.statik.navigation.Route

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavigationBar {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route::class.qualifiedName,
                onClick = { navController.navigate(item.route) },
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(text = item.label) }
            )
        }
    }
}

private val bottomNavItems = listOf(
    BottomNavItem(Route.RootDestination.Dashboard, "Dashboard", Icons.Filled.Home),
    BottomNavItem(Route.RootDestination.Information, "Information", Icons.Filled.Info),
    BottomNavItem(Route.RootDestination.Tools, "Tools", Icons.Filled.Build),
    BottomNavItem(Route.RootDestination.Settings, "Settings", Icons.Filled.Settings),
)

private data class BottomNavItem(
    val route: Route,
    val label: String,
    val icon: ImageVector
)