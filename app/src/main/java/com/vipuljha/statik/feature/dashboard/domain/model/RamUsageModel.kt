package com.vipuljha.statik.feature.dashboard.domain.model

data class RamUsageModel(
    val totalRam: Long,
    val usedRam: Long,
    val freeRam: Long,
    val usedPercentage: Float
)
