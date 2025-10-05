package com.vipuljha.statik.feature.dashboard.domain.model

data class BatteryUsageModel(
    val percentage: Int = 0,
    val isCharging: Boolean = false,
    val temperatureCelsius: Float = 0f,
    val voltage: Int = 0
)
