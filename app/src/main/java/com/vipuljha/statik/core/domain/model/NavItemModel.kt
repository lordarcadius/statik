package com.vipuljha.statik.core.domain.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.vipuljha.statik.navigation.Route

data class NavItemModel(
    val route: Route,
    val label: String,
    val icon: ImageVector
)