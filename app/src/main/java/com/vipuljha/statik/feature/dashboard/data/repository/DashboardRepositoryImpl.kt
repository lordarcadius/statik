package com.vipuljha.statik.feature.dashboard.data.repository

import com.vipuljha.statik.core.util.Constants.REALTIME_DATA_FETCH_DELAY
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
        val cpuDir = File("/sys/devices/system/cpu/")
        val result = mutableListOf<PerCoreFreqModel>()

        cpuDir.listFiles { file -> file.name.matches(Regex("cpu[0-9]+")) }?.forEach { cpuCore ->
            val coreName = cpuCore.name

            val minFreq = readFile("${cpuCore.absolutePath}/cpufreq/cpuinfo_min_freq")
            val maxFreq = readFile("${cpuCore.absolutePath}/cpufreq/cpuinfo_max_freq")
            val curFreq = readFile("${cpuCore.absolutePath}/cpufreq/scaling_cur_freq")

            result.add(
                PerCoreFreqModel(
                    core = coreName,
                    minFrequency = minFreq,
                    maxFrequency = maxFreq,
                    currentFrequency = curFreq
                )
            )
        }

        return result
    }

    private fun readFile(path: String): String {
        return try {
            File(path).readText().trim()
        } catch (e: Exception) {
            "N/A"
        }
    }
}