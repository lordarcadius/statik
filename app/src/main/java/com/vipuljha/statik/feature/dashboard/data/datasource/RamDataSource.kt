package com.vipuljha.statik.feature.dashboard.data.datasource

import android.app.ActivityManager
import android.content.Context
import com.vipuljha.statik.core.util.Constants.REALTIME_DATA_FETCH_DELAY
import com.vipuljha.statik.feature.dashboard.domain.model.RamUsageModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import java.io.File
import javax.inject.Inject

class RamDataSource @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    // Cache total RAM once since it doesn't change
    private val totalRamBytes: Long by lazy {
        getTotalRam()
    }

    fun observeRamInfo(): Flow<RamUsageModel> = flow {
        while (currentCoroutineContext().isActive) {
            emit(getRamInfo())
            delay(REALTIME_DATA_FETCH_DELAY)
        }
    }.flowOn(Dispatchers.IO)

    private fun getTotalRam(): Long {
        return try {
            val activityManager =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val memInfo = ActivityManager.MemoryInfo()
            activityManager.getMemoryInfo(memInfo)

            // For older APIs, fallback to /proc/meminfo
            if (memInfo.totalMem > 0) {
                memInfo.totalMem
            } else {
                val memInfoFile = File("/proc/meminfo")
                val firstLine = memInfoFile.useLines { it.firstOrNull() }
                firstLine?.replace(Regex("\\D+"), "")?.toLongOrNull()?.times(1024) ?: 0L
            }
        } catch (e: Exception) {
            0L
        }
    }

    /** Returns real-time RAM usage */
    private fun getRamInfo(): RamUsageModel {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memInfo)

        val freeRam = memInfo.availMem
        val usedRam = totalRamBytes - freeRam
        val usedPercentage = if (totalRamBytes > 0)
            (usedRam.toDouble() / totalRamBytes * 100).toFloat()
        else 0f

        return RamUsageModel(
            totalRam = totalRamBytes,
            usedRam = usedRam,
            freeRam = freeRam,
            usedPercentage = usedPercentage
        )
    }
}