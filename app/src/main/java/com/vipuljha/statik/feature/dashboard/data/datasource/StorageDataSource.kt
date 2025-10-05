package com.vipuljha.statik.feature.dashboard.data.datasource

import android.content.Context
import android.os.StatFs
import com.vipuljha.statik.core.util.Constants.REALTIME_DATA_FETCH_DELAY
import com.vipuljha.statik.feature.dashboard.domain.model.MemoryUsageModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageDataSource @Inject constructor(
    @param:ApplicationContext private val context: Context
) {

    val totalStorage by lazy { calculateTotalStorage() }

    fun observeStorageInfo(): Flow<MemoryUsageModel> = flow {

        while (currentCoroutineContext().isActive) {
            emit(getStorageInfo(totalStorage))
            delay(REALTIME_DATA_FETCH_DELAY)
        }
    }.flowOn(Dispatchers.IO)

    /** Total storage in bytes (cached because it doesn't change) */
    private fun calculateTotalStorage(): Long {
        val stat = StatFs(context.filesDir.absolutePath)
        val total = stat.blockCountLong * stat.blockSizeLong
        return total
    }

    /** Returns real-time storage usage */
    private fun getStorageInfo(totalStorage: Long): MemoryUsageModel {
        val stat = StatFs(context.filesDir.absolutePath)
        val free = stat.availableBlocksLong * stat.blockSizeLong
        val used = totalStorage - free
        val usedPercentage = if (totalStorage > 0)
            (used.toDouble() / totalStorage * 100).toFloat()
        else 0f

        return MemoryUsageModel(
            totalBytes = totalStorage,
            usedBytes = used,
            freeBytes = free,
            usedPercentage = usedPercentage
        )
    }

}