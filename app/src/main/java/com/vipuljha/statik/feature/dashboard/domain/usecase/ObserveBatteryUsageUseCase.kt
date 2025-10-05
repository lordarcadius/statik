package com.vipuljha.statik.feature.dashboard.domain.usecase

import com.vipuljha.statik.core.domain.BaseUseCase
import com.vipuljha.statik.core.domain.NoParams
import com.vipuljha.statik.feature.dashboard.domain.model.BatteryUsageModel
import com.vipuljha.statik.feature.dashboard.domain.repository.DashboardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveBatteryUsageUseCase @Inject constructor(
    private val repository: DashboardRepository
) : BaseUseCase<NoParams, BatteryUsageModel> {
    override fun invoke(params: NoParams): Flow<BatteryUsageModel> {
        return repository.observeBatteryInfo()
    }
}