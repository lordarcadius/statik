package com.vipuljha.statik.feature.dashboard.data.repository

import com.vipuljha.statik.feature.dashboard.data.datasource.CpuDataSource
import com.vipuljha.statik.feature.dashboard.data.datasource.RamDataSource
import com.vipuljha.statik.feature.dashboard.data.datasource.StorageDataSource
import com.vipuljha.statik.feature.dashboard.domain.model.MemoryUsageModel
import com.vipuljha.statik.feature.dashboard.domain.model.PerCoreFreqModel
import com.vipuljha.statik.feature.dashboard.domain.repository.DashboardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardRepositoryImpl @Inject constructor(
    private val cpuDataSource: CpuDataSource,
    private val ramDataSource: RamDataSource,
    private val storageDataSource: StorageDataSource
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
}