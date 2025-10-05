package com.vipuljha.statik.feature.dashboard.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vipuljha.statik.core.domain.NoParams
import com.vipuljha.statik.feature.dashboard.domain.model.MemoryUsageModel
import com.vipuljha.statik.feature.dashboard.domain.model.PerCoreFreqModel
import com.vipuljha.statik.feature.dashboard.domain.usecase.ObserveCpuFrequenciesUseCase
import com.vipuljha.statik.feature.dashboard.domain.usecase.ObserveRamUsageUseCase
import com.vipuljha.statik.feature.dashboard.domain.usecase.ObserveStorageUsageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    observeCpuFrequenciesUseCase: ObserveCpuFrequenciesUseCase,
    observeRamUsageUseCase: ObserveRamUsageUseCase,
    observeStorageUsageUseCase: ObserveStorageUsageUseCase
) : ViewModel() {
    val perCoreFrequencies: StateFlow<List<PerCoreFreqModel>> =
        observeCpuFrequenciesUseCase(NoParams)
            .catch { exception ->
                Log.e(TAG, "Error observing CPU frequency", exception)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    val ramUsage: StateFlow<MemoryUsageModel> =
        observeRamUsageUseCase(NoParams)
            .catch { exception ->
                Log.e(TAG, "Error observing RAM usage", exception)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = MemoryUsageModel(0, 0, 0, 0f)
            )

    val storageUsage: StateFlow<MemoryUsageModel> =
        observeStorageUsageUseCase(NoParams)
            .catch { exception ->
                Log.e(TAG, "Error observing storage usage", exception)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = MemoryUsageModel(0, 0, 0, 0f)
            )

    private companion object {
        const val TAG = "DashboardViewModel"
    }
}