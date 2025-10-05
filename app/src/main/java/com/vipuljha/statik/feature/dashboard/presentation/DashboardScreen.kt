package com.vipuljha.statik.feature.dashboard.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.vipuljha.statik.core.util.Helper.formatBytes
import com.vipuljha.statik.core.util.Helper.formatNetworkSpeed
import com.vipuljha.statik.core.util.Helper.formatPercentage
import com.vipuljha.statik.feature.dashboard.presentation.composable.CpuCoreItem

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = hiltViewModel<DashboardViewModel>()
) {
    val cores = viewModel.perCoreFrequencies.collectAsState()
    val ramUsage = viewModel.ramUsage.collectAsState()
    val storageUsage = viewModel.storageUsage.collectAsState()
    val batteryUsage = viewModel.batteryUsage.collectAsState()
    val networkUsage = viewModel.networkUsage.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                columns = GridCells.Adaptive(minSize = 138.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(cores.value.size) { index ->
                    CpuCoreItem(core = cores.value[index])
                }
            }
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

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            "Network: ${networkUsage.value.type} \nConnected: ${networkUsage.value.isConnected} \nDownload Speed: ${
                formatNetworkSpeed(
                    networkUsage.value.downloadSpeed
                )
            } \nUpload Speed: ${formatNetworkSpeed(networkUsage.value.uploadSpeed)}"
        )
    }
}