package com.vipuljha.statik.feature.information

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.vipuljha.statik.core.domain.model.NavItemModel
import com.vipuljha.statik.feature.information.tabs.AppsTab
import com.vipuljha.statik.feature.information.tabs.BatteryTab
import com.vipuljha.statik.feature.information.tabs.HardwareTab
import com.vipuljha.statik.feature.information.tabs.SoftwareTab
import com.vipuljha.statik.navigation.Route
import kotlinx.coroutines.launch

@Composable
fun InformationScreen(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState { infoTabItems.size }
    val scope = rememberCoroutineScope()

    BackHandler(enabled = pagerState.currentPage != 0) {
        scope.launch { pagerState.animateScrollToPage(0) }
    }

    Column(modifier = modifier.fillMaxSize()) {
        ScrollableTabRow(selectedTabIndex = pagerState.currentPage) {
            infoTabItems.forEachIndexed { index, item ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(item.label) },
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (infoTabItems[page].route) {
                Route.InfoTabDestination.Hardware -> HardwareTab()
                Route.InfoTabDestination.Software -> SoftwareTab()
                Route.InfoTabDestination.Battery -> BatteryTab()
                Route.InfoTabDestination.Apps -> AppsTab()
                else -> Text("Page not found!")
            }
        }
    }
}

private val infoTabItems = listOf(
    NavItemModel(Route.InfoTabDestination.Hardware, "Hardware", Icons.Filled.Memory),
    NavItemModel(Route.InfoTabDestination.Software, "Software", Icons.Filled.Storage),
    NavItemModel(Route.InfoTabDestination.Battery, "Battery", Icons.Filled.BatteryFull),
    NavItemModel(Route.InfoTabDestination.Apps, "Apps", Icons.Filled.Android)
)
