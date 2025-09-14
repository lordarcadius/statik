package com.vipuljha.statik.feature.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vipuljha.statik.feature.dashboard.presentation.DashboardScreen
import com.vipuljha.statik.feature.information.InformationScreen
import com.vipuljha.statik.feature.settings.SettingScreen
import com.vipuljha.statik.feature.tools.ToolScreen
import com.vipuljha.statik.navigation.Route

@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Route.RootDestination.Dashboard
        ) {
            composable<Route.RootDestination.Dashboard> { DashboardScreen() }
            composable<Route.RootDestination.Information> { InformationScreen() }
            composable<Route.RootDestination.Tools> { ToolScreen() }
            composable<Route.RootDestination.Settings> { SettingScreen() }
        }
    }
}
