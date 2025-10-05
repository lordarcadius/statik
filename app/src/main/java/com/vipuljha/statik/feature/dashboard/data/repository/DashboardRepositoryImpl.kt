package com.vipuljha.statik.feature.dashboard.data.repository

import com.vipuljha.statik.feature.dashboard.data.datasource.BatteryDataSource
import com.vipuljha.statik.feature.dashboard.data.datasource.CpuDataSource
import com.vipuljha.statik.feature.dashboard.data.datasource.NetworkDataSource
import com.vipuljha.statik.feature.dashboard.data.datasource.RamDataSource
import com.vipuljha.statik.feature.dashboard.data.datasource.StorageDataSource
import com.vipuljha.statik.feature.dashboard.domain.model.BatteryUsageModel
import com.vipuljha.statik.feature.dashboard.domain.model.MemoryUsageModel
import com.vipuljha.statik.feature.dashboard.domain.model.NetworkUsageModel
import com.vipuljha.statik.feature.dashboard.domain.model.PerCoreFreqModel
import com.vipuljha.statik.feature.dashboard.domain.repository.DashboardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardRepositoryImpl @Inject constructor(
    private val cpuDataSource: CpuDataSource,
    private val ramDataSource: RamDataSource,
    private val storageDataSource: StorageDataSource,
    private val batteryDataSource: BatteryDataSource,
    private val networkDataSource: NetworkDataSource
) : DashboardRepository {
    override fun observerPerCoreFrequency(): Flow<List<PerCoreFreqModel>> {
        return cpuDataSource.observerPerCoreFrequency()
    }

    override fun observeRamInfo(): Flow<MemoryUsageModel> {
        return ramDataSource.observeRamInfo()
    }

    override fun observeStorageInfo(): Flow<MemoryUsageModel> {
        return storageDataSource.observeStorageInfo()
    }

    override fun observeBatteryInfo(): Flow<BatteryUsageModel> {
        return batteryDataSource.observeBatteryInfo()
    }

    override fun observeNetworkInfo(): Flow<NetworkUsageModel> {
        return networkDataSource.observeNetworkInfo()
    }
}