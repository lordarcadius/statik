package com.vipuljha.statik.feature.dashboard.domain.repository

import com.vipuljha.statik.feature.dashboard.domain.model.BatteryUsageModel
import com.vipuljha.statik.feature.dashboard.domain.model.MemoryUsageModel
import com.vipuljha.statik.feature.dashboard.domain.model.PerCoreFreqModel
import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    fun observerPerCoreFrequency(): Flow<List<PerCoreFreqModel>>
    fun observeRamInfo(): Flow<MemoryUsageModel>
    fun observeStorageInfo(): Flow<MemoryUsageModel>
    fun observeBatteryInfo(): Flow<BatteryUsageModel>
}