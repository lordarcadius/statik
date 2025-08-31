package com.vipuljha.statik.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    // Top-level routes
    @Serializable
    data object Onboarding : Route

    @Serializable
    data object Root : Route

    // Root bottom nav destinations
    sealed interface RootDestination : Route {
        @Serializable
        data object Dashboard : RootDestination

        @Serializable
        data object Information : RootDestination

        @Serializable
        data object Tools : RootDestination

        @Serializable
        data object Settings : RootDestination
    }

    // Information tab destinations (inside Information)
    sealed interface InfoTabDestination : RootDestination {
        @Serializable
        data object Hardware : InfoTabDestination

        @Serializable
        data object Software : InfoTabDestination

        @Serializable
        data object Battery : InfoTabDestination

        @Serializable
        data object Sensors : InfoTabDestination

        @Serializable
        data object Network : InfoTabDestination

        @Serializable
        data object Apps : InfoTabDestination
    }

    @Serializable
    data class AppDetails(val packageName: String) : Route
}
