package com.vipuljha.statik.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vipuljha.statik.feature.home.HomeScreen
import com.vipuljha.statik.feature.onboarding.OnboardingScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    startDestination: Route = Route.Onboarding
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
    ) {
        composable<Route.Onboarding> {
            OnboardingScreen(
                modifier = modifier,
                onFinished = {
                    navHostController.navigate(Route.Root) {
                        popUpTo(Route.Onboarding) { inclusive = true }
                    }
                }
            )
        }

        composable<Route.Root> {
            HomeScreen()
        }
    }
}