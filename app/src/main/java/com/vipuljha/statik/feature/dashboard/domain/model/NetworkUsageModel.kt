package com.vipuljha.statik.feature.dashboard.domain.model

data class NetworkUsageModel(
    val type: String,
    val isConnected: Boolean,
    val downloadSpeed: Long,
    val uploadSpeed: Long
)
