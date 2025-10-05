package com.vipuljha.statik.feature.dashboard.domain.model

data class MemoryUsageModel(
    val totalBytes: Long,
    val usedBytes: Long,
    val freeBytes: Long,
    val usedPercentage: Float
)
