package com.vipuljha.statik.feature.dashboard.domain.model

data class NetworkUsageModel(
    val type: String = "UNKNOWN",
    val isConnected: Boolean = false,
    val downloadSpeed: Long = 0L,
    val uploadSpeed: Long = 0L
)
