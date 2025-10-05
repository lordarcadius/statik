package com.vipuljha.statik.feature.dashboard.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vipuljha.statik.core.domain.NoParams
import com.vipuljha.statik.feature.dashboard.domain.model.BatteryUsageModel
import com.vipuljha.statik.feature.dashboard.domain.model.MemoryUsageModel
import com.vipuljha.statik.feature.dashboard.domain.model.PerCoreFreqModel
import com.vipuljha.statik.feature.dashboard.domain.usecase.ObserveBatteryUsageUseCase
import com.vipuljha.statik.feature.dashboard.domain.usecase.ObserveCpuFrequenciesUseCase
import com.vipuljha.statik.feature.dashboard.domain.usecase.ObserveRamUsageUseCase
import com.vipuljha.statik.feature.dashboard.domain.usecase.ObserveStorageUsageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    observeCpuFrequenciesUseCase: ObserveCpuFrequenciesUseCase,
    observeRamUsageUseCase: ObserveRamUsageUseCase,
    observeStorageUsageUseCase: ObserveStorageUsageUseCase,
    observeBatteryUsageUseCase: ObserveBatteryUsageUseCase,
) : ViewModel() {

    private val initialMemoryUsage = MemoryUsageModel(
        totalBytes = 0,
        usedBytes = 0,
        freeBytes = 0,
        usedPercentage = 0f
    )

    private val initialBatteryUsage = BatteryUsageModel(
        percentage = 0,
        isCharging = false,
        temperatureCelsius = 0f,
        voltage = 0
    )

    val perCoreFrequencies: StateFlow<List<PerCoreFreqModel>> =
        observeCpuFrequenciesUseCase(NoParams)
            .toStateFlow(emptyList(), "CPU frequency")

    val ramUsage: StateFlow<MemoryUsageModel> =
        observeRamUsageUseCase(NoParams)
            .toStateFlow(initialMemoryUsage, "RAM usage")

    val storageUsage: StateFlow<MemoryUsageModel> =
        observeStorageUsageUseCase(NoParams)
            .toStateFlow(initialMemoryUsage, "Storage usage")

    val batteryUsage: StateFlow<BatteryUsageModel> =
        observeBatteryUsageUseCase(NoParams)
            .toStateFlow(initialBatteryUsage, "Battery usage")


    private fun <T> Flow<T>.toStateFlow(
        initialValue: T,
        tag: String
    ): StateFlow<T> = this
        .catch { exception -> Log.e(TAG, "Error observing $tag", exception) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = initialValue
        )

    private companion object {
        const val TAG = "DashboardViewModel"
    }
}