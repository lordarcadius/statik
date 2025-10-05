package com.vipuljha.statik.feature.dashboard.domain.model

data class NetworkUsageModel(
    val type: String,
    val isConnected: Boolean,
    val downloadSpeedBytesPerSec: Long,
    val uploadSpeedBytesPerSec: Long
)
