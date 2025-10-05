package com.vipuljha.statik.feature.dashboard.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.vipuljha.statik.core.util.Helper.formatBytes
import com.vipuljha.statik.core.util.Helper.formatFreq
import com.vipuljha.statik.core.util.Helper.formatPercentage

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = hiltViewModel<DashboardViewModel>()
) {
    val cores = viewModel.perCoreFrequencies.collectAsState()
    val ramUsage = viewModel.ramUsage.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        cores.value.forEach { core ->
            Text(
                text = "${core.core} → ${formatFreq(core.currentFrequency)} " +
                        "(min ${formatFreq(core.minFrequency)}, max ${formatFreq(core.maxFrequency)})"
            )
        }

        Text(
            "Total RAM: ${formatBytes(ramUsage.value.totalRam)} \nUsed RAM: ${formatBytes(ramUsage.value.usedRam)} \nFree RAM: ${
                formatBytes(
                    ramUsage.value.freeRam
                )
            } \nUsed Percentage: ${formatPercentage(ramUsage.value.usedPercentage)}"
        )
    }
}