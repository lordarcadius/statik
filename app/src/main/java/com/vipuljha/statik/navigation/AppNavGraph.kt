package com.vipuljha.statik.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.vipuljha.statik.core.util.back
import com.vipuljha.statik.core.util.open
import com.vipuljha.statik.feature.home.HomeScreen
import com.vipuljha.statik.feature.onboarding.OnboardingScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    startDestination: Route = Route.Onboarding
) {
    val backStack = rememberNavBackStack(startDestination)
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.back() },
        entryProvider = entryProvider {
            entry<Route.Onboarding> {
                OnboardingScreen() {
                    backStack.open(Route.Root)
                }
            }
            entry<Route.Root> {
                HomeScreen()
            }
        }
    )
}