package com.vipuljha.statik.feature.dashboard.domain.model

data class MemoryUsageModel(
    val totalBytes: Long = 0L,
    val usedBytes: Long = 0L,
    val freeBytes: Long = 0L,
    val usedPercentage: Float = 0f
)
