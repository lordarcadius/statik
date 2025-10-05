package com.vipuljha.statik.feature.dashboard.data.datasource

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.TrafficStats
import com.vipuljha.statik.core.util.Constants.INTERNET_SPEED_FETCH_DELAY
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
        var lastTime = System.currentTimeMillis()

        if (lastRxBytes == TrafficStats.UNSUPPORTED.toLong() || lastTxBytes == TrafficStats.UNSUPPORTED.toLong()) {
            emit(NetworkUsageModel("UNSUPPORTED", false, 0L, 0L))
            return@flow
        }

        while (currentCoroutineContext().isActive) {
            delay(INTERNET_SPEED_FETCH_DELAY)

            val currentRxBytes = TrafficStats.getTotalRxBytes()
            val currentTxBytes = TrafficStats.getTotalTxBytes()
            val currentTime = System.currentTimeMillis()

            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            val isConnected = capabilities != null
            val type = when {
                !isConnected -> "NONE"
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "WIFI"
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "CELLULAR"
                else -> "UNKNOWN"
            }

            if (currentRxBytes > lastRxBytes || currentTxBytes > lastTxBytes) {
                val timeElapsed = (currentTime - lastTime) / 1000.0f

                // Ensure time has actually passed to avoid division by zero.
                if (timeElapsed > 0f) {
                    val downloadSpeed = ((currentRxBytes - lastRxBytes) / timeElapsed).toLong()
                    val uploadSpeed = ((currentTxBytes - lastTxBytes) / timeElapsed).toLong()

                    emit(
                        NetworkUsageModel(
                            type = type,
                            isConnected = isConnected,
                            downloadSpeed = downloadSpeed.coerceAtLeast(0L),
                            uploadSpeed = uploadSpeed.coerceAtLeast(0L)
                        )
                    )

                    lastRxBytes = currentRxBytes
                    lastTxBytes = currentTxBytes
                    lastTime = currentTime
                }
            } else {
                emit(
                    NetworkUsageModel(
                        type = type,
                        isConnected = isConnected,
                        downloadSpeed = 0L,
                        uploadSpeed = 0L
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)
}