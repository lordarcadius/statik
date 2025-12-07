package com.vipuljha.statik.feature.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.vipuljha.statik.feature.dashboard.presentation.DashboardScreen
import com.vipuljha.statik.feature.information.InformationScreen
import com.vipuljha.statik.feature.settings.SettingScreen
import com.vipuljha.statik.feature.tools.ToolScreen
import com.vipuljha.statik.navigation.Route
import com.vipuljha.statik.navigation.TopLevelBackStack

@Composable
fun HomeScreen() {
    val bottomNavBackStack =
        remember { TopLevelBackStack<Route.RootDestination>(Route.RootDestination.Dashboard) }

    val bottomNavItems = remember {
        listOf(
            Route.RootDestination.Dashboard,
            Route.RootDestination.Information,
            Route.RootDestination.Tools,
            Route.RootDestination.Settings,
        )
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = bottomNavItems,
                current = bottomNavBackStack.topLevelKey,
                onSelect = { destination -> bottomNavBackStack.addTopLevel(destination) }
            )
        }
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier.padding(innerPadding),
            backStack = bottomNavBackStack.backStack,
            onBack = { bottomNavBackStack.removeLast() },
            entryProvider = entryProvider {
                entry<Route.RootDestination.Dashboard> { DashboardScreen() }
                entry<Route.RootDestination.Information> { InformationScreen() }
                entry<Route.RootDestination.Tools> { ToolScreen() }
                entry<Route.RootDestination.Settings> { SettingScreen() }
            }
        )
    }
}
