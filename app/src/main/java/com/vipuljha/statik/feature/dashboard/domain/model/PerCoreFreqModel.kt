package com.vipuljha.statik.feature.dashboard.domain.model

data class PerCoreFreqModel(
    var core: String,
    var minFrequency: String,
    var maxFrequency: String,
    var currentFrequency: String
)
