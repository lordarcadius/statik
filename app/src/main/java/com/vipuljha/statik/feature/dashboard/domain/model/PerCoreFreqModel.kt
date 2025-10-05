package com.vipuljha.statik.feature.dashboard.domain.model

data class PerCoreFreqModel(
    var core: String = "cpu0",
    var minFrequency: Long = 0L,
    var maxFrequency: Long = 0L,
    var currentFrequency: Long = 0L
)
