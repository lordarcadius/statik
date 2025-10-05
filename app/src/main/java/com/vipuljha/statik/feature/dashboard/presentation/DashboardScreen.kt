package com.vipuljha.statik.feature.dashboard.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
    val storageUsage = viewModel.storageUsage.collectAsState()
    val batteryUsage = viewModel.batteryUsage.collectAsState()

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

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            "Total RAM: ${formatBytes(ramUsage.value.totalBytes)} \nUsed RAM: ${formatBytes(ramUsage.value.usedBytes)} \nFree RAM: ${
                formatBytes(
                    ramUsage.value.freeBytes
                )
            } \nUsed Percentage: ${formatPercentage(ramUsage.value.usedPercentage)}"
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            "Total Storage: ${formatBytes(storageUsage.value.totalBytes)}\nUsed Storage: ${
                formatBytes(
                    storageUsage.value.usedBytes
                )
            }\nFree Storage: ${formatBytes(storageUsage.value.freeBytes)}\nUsed Percentage: ${
                formatPercentage(
                    storageUsage.value.usedPercentage
                )
            }"
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text("Battery: ${batteryUsage.value.percentage}% \nCharging: ${batteryUsage.value.isCharging} \nTemperature: ${batteryUsage.value.temperatureCelsius}°C \nVoltage: ${batteryUsage.value.voltage}mV")
    }
}