package com.vipuljha.statik.feature.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vipuljha.statik.core.domain.NoParams
import com.vipuljha.statik.feature.dashboard.domain.model.PerCoreFreqModel
import com.vipuljha.statik.feature.dashboard.domain.usecase.ObserveCpuFrequenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    observeCpuFrequenciesUseCase: ObserveCpuFrequenciesUseCase
) : ViewModel() {
    val perCoreFrequencies: StateFlow<List<PerCoreFreqModel>> =
        observeCpuFrequenciesUseCase(NoParams)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
}