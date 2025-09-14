package com.vipuljha.statik.feature.dashboard.domain.repository

import com.vipuljha.statik.feature.dashboard.domain.model.PerCoreFreqModel
import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    fun observerPerCoreFrequency(): Flow<List<PerCoreFreqModel>>
}