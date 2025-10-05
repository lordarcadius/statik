package com.vipuljha.statik.feature.dashboard.data.datasource

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.TrafficStats
import com.vipuljha.statik.core.util.Constants.REALTIME_DATA_FETCH_DELAY
import com.vipuljha.statik.feature.dashboard.domain.model.NetworkUsageModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    fun observeNetworkInfo(): Flow<NetworkUsageModel> = flow {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        var lastRxBytes = TrafficStats.getTotalRxBytes()
        var lastTxBytes = TrafficStats.getTotalTxBytes()

        while (currentCoroutineContext().isActive) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            val isConnected = capabilities != null
            val type = when {
                capabilities == null -> "NONE"
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "WIFI"
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "CELLULAR"
                else -> "UNKNOWN"
            }

            val currentRxBytes = TrafficStats.getTotalRxBytes()
            val currentTxBytes = TrafficStats.getTotalTxBytes()
            val intervalSec = REALTIME_DATA_FETCH_DELAY.coerceAtLeast(1_000L) / 1000f

            val downloadSpeed = ((currentRxBytes - lastRxBytes) / intervalSec).toLong()
            val uploadSpeed = ((currentTxBytes - lastTxBytes) / intervalSec).toLong()


            lastRxBytes = currentRxBytes
            lastTxBytes = currentTxBytes

            emit(NetworkUsageModel(type, isConnected, downloadSpeed, uploadSpeed))
            delay(REALTIME_DATA_FETCH_DELAY)
        }
    }.flowOn(Dispatchers.IO)
}