package com.vipuljha.statik.feature.dashboard.domain.model

data class BatteryUsageModel(
    val percentage: Int,
    val isCharging: Boolean,
    val temperatureCelsius: Float,
    val voltage: Int
)
