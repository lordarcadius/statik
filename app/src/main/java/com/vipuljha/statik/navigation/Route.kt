package com.vipuljha.statik.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {

    // Top-level routes
    @Serializable
    data object Onboarding : Route

    @Serializable
    data object Root : Route

    // Root bottom nav destinations
    @Serializable
    sealed interface RootDestination : Route {

        val label: String
        val icon: ImageVector

        @Serializable
        data object Dashboard : RootDestination {
            override val label: String = "Dashboard"
            override val icon: ImageVector = Icons.Filled.Home
        }

        @Serializable
        data object Information : RootDestination {
            override val label: String = "Information"
            override val icon: ImageVector = Icons.Filled.Info
        }

        @Serializable
        data object Tools : RootDestination {
            override val label: String = "Tools"
            override val icon: ImageVector = Icons.Filled.Build
        }

        @Serializable
        data object Settings : RootDestination {
            override val label: String = "Settings"
            override val icon: ImageVector = Icons.Filled.Settings
        }
    }

    // Information tab destinations (inside Information)
    @Serializable
    sealed interface InfoTabDestination : Route {
        val label: String

        @Serializable
        data object Hardware : InfoTabDestination {
            override val label: String = "Hardware"
        }

        @Serializable
        data object Software : InfoTabDestination {
            override val label: String = "Software"
        }

        @Serializable
        data object Battery : InfoTabDestination {
            override val label: String = "Battery"
        }

        @Serializable
        data object Sensors : InfoTabDestination {
            override val label: String = "Sensors"
        }

        @Serializable
        data object Network : InfoTabDestination {
            override val label: String = "Network"
        }

        @Serializable
        data object Apps : InfoTabDestination {
            override val label: String = "Apps"
        }
    }
}

class TopLevelBackStack<T : Any>(startKey: T) {

    // Maintain a stack for each top level route
    private var topLevelStacks: LinkedHashMap<T, SnapshotStateList<T>> = linkedMapOf(
        startKey to mutableStateListOf(startKey)
    )

    // Expose the current top level route for consumers
    var topLevelKey by mutableStateOf(startKey)
        private set

    // Expose the back stack so it can be rendered by the NavDisplay
    val backStack = mutableStateListOf(startKey)

    private fun updateBackStack() =
        backStack.apply {
            clear()
            addAll(topLevelStacks.flatMap { it.value })
        }

    fun addTopLevel(key: T) {

        // If the top level doesn't exist, add it
        if (topLevelStacks[key] == null) {
            topLevelStacks[key] = mutableStateListOf(key)
        } else {
            // Otherwise just move it to the end of the stacks
            topLevelStacks.apply {
                remove(key)?.let {
                    put(key, it)
                }
            }
        }
        topLevelKey = key
        updateBackStack()
    }

    fun add(key: T) {
        topLevelStacks[topLevelKey]?.add(key)
        updateBackStack()
    }

    fun removeLast() {
        val removedKey = topLevelStacks[topLevelKey]?.removeLastOrNull()
        // If the removed key was a top level key, remove the associated top level stack
        topLevelStacks.remove(removedKey)
        topLevelKey = topLevelStacks.keys.last()
        updateBackStack()
    }
}

