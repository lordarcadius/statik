package com.vipuljha.statik.feature.dashboard.domain.model

data class PerCoreFreqModel(
    var core: String,
    var minFrequency: Long,
    var maxFrequency: Long,
    var currentFrequency: Long
)
