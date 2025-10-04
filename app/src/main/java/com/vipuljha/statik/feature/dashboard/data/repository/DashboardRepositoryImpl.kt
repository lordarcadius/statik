package com.vipuljha.statik.feature.dashboard.data.repository

import com.vipuljha.statik.core.util.Constants.CPU_CORE_REGEX
import com.vipuljha.statik.core.util.Constants.REALTIME_DATA_FETCH_DELAY
import com.vipuljha.statik.core.util.Helper.getFakeEmulatorFreqData
import com.vipuljha.statik.core.util.Helper.isEmulator
import com.vipuljha.statik.core.util.Helper.readFile
import com.vipuljha.statik.feature.dashboard.domain.model.PerCoreFreqModel
import com.vipuljha.statik.feature.dashboard.domain.repository.DashboardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardRepositoryImpl @Inject constructor() : DashboardRepository {

    // Cache min/max values once at startup because they will never change
    private val cachedCoreMinMax: List<Pair<String, Pair<Long, Long>>> by lazy {
        loadCoreMinMaxFrequencies()
    }

    override fun observerPerCoreFrequency(): Flow<List<PerCoreFreqModel>> = flow {
        while (currentCoroutineContext().isActive) {
            val data = getRealTimePerCoreFrequency()
            emit(data)
            delay(REALTIME_DATA_FETCH_DELAY)
        }
    }.flowOn(Dispatchers.IO)


    private fun getRealTimePerCoreFrequency(): List<PerCoreFreqModel> {
        return if (isEmulator()) {
            getFakeEmulatorFreqData()
        } else {
            getDeviceFreqData()
        }
    }

    private fun getDeviceFreqData(): List<PerCoreFreqModel> {
        if (cachedCoreMinMax.isEmpty()) return emptyList()
        return cachedCoreMinMax.map { (coreName, minMaxPair) ->
            val (minFreq, maxFreq) = minMaxPair
            val curFreqPath = "$CPU_DEVICES_PATH$coreName/$CPU_SCALING_CURRENT_FREQ"

            // Read current CPU frequency; fallback to 0 if reading fails or value is invalid
            // Using 0 ensures the app won't crash and the UI can handle missing data gracefully
            val curFreq = readFile(curFreqPath).toLongOrNull() ?: 0

            PerCoreFreqModel(
                core = coreName,
                minFrequency = minFreq,
                maxFrequency = maxFreq,
                currentFrequency = curFreq
            )
        }
    }

    private fun loadCoreMinMaxFrequencies(): List<Pair<String, Pair<Long, Long>>> {
        val cpuDir = File(CPU_DEVICES_PATH)
        val cpuCores = cpuDir.listFiles { file -> file.name.matches(CPU_CORE_REGEX) }
            ?: return emptyList()

        val coreFrequencies = cpuCores.mapNotNull { cpuCore ->
            val minFreq = readFile("${cpuCore.absolutePath}/$CPU_INFO_MIN_FREQ").toLongOrNull()
            val maxFreq = readFile("${cpuCore.absolutePath}/$CPU_INFO_MAX_FREQ").toLongOrNull()

            // Only include cores where both min and max frequencies are valid
            if (minFreq != null && maxFreq != null) {
                cpuCore.name to (minFreq to maxFreq)
            } else null
        }

        // Sort cores in descending order of max frequency
        return coreFrequencies.sortedBy { it.second.second }
    }

    private companion object {
        const val CPU_DEVICES_PATH = "/sys/devices/system/cpu/"
        const val CPU_SCALING_CURRENT_FREQ = "cpufreq/scaling_cur_freq"
        const val CPU_INFO_MIN_FREQ = "cpufreq/cpuinfo_min_freq"
        const val CPU_INFO_MAX_FREQ = "cpufreq/cpuinfo_max_freq"
    }
}