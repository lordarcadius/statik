package com.vipuljha.statik.feature.dashboard.domain.usecase

import com.vipuljha.statik.core.domain.BaseUseCase
import com.vipuljha.statik.core.domain.NoParams
import com.vipuljha.statik.feature.dashboard.domain.model.PerCoreFreqModel
import com.vipuljha.statik.feature.dashboard.domain.repository.DashboardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCpuFrequenciesUseCase @Inject constructor(
    private val repository: DashboardRepository
) : BaseUseCase<NoParams, List<PerCoreFreqModel>> {
    override fun invoke(params: NoParams): Flow<List<PerCoreFreqModel>> {
        return repository.observerPerCoreFrequency()
    }
}