package com.vipuljha.statik.feature.dashboard.data.repository

import com.vipuljha.statik.core.util.Constants.CPU_CORE_REGEX
import com.vipuljha.statik.core.util.Constants.REALTIME_DATA_FETCH_DELAY
import com.vipuljha.statik.core.util.Helper.getFakeEmulatorFreqData
import com.vipuljha.statik.core.util.Helper.isEmulator
import com.vipuljha.statik.core.util.Helper.readFile
import com.vipuljha.statik.feature.dashboard.domain.model.PerCoreFreqModel
import com.vipuljha.statik.feature.dashboard.domain.repository.DashboardRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardRepositoryImpl @Inject constructor() : DashboardRepository {
    override fun observerPerCoreFrequency(): Flow<List<PerCoreFreqModel>> = flow {
        while (true) {
            emit(getRealTimePerCoreFrequency())
            delay(REALTIME_DATA_FETCH_DELAY)
        }
    }

    private fun getRealTimePerCoreFrequency(): List<PerCoreFreqModel> {
        return if (isEmulator()) {
            getFakeEmulatorFreqData()
        } else {
            getDeviceFreqData()
        }
    }

    private fun getDeviceFreqData(): List<PerCoreFreqModel> {
        val cpuDir = File("/sys/devices/system/cpu/")
        return cpuDir.listFiles { file -> file.name.matches(CPU_CORE_REGEX) }
            ?.map { cpuCore ->
                PerCoreFreqModel(
                    core = cpuCore.name,
                    minFrequency = readFile("${cpuCore.absolutePath}/cpufreq/cpuinfo_min_freq"),
                    maxFrequency = readFile("${cpuCore.absolutePath}/cpufreq/cpuinfo_max_freq"),
                    currentFrequency = readFile("${cpuCore.absolutePath}/cpufreq/scaling_cur_freq")
                )
            } ?: emptyList()
    }
}
