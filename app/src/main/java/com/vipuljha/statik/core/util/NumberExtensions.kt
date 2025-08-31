package com.vipuljha.statik.core.util

import java.util.Locale
import kotlin.math.pow
import kotlin.math.round

fun Long.formatBytes(): String {
    if (this < 1024) return "$this B"

    val units = arrayOf("KB", "MB", "GB", "TB", "PB", "EB")
    var size = this.toDouble()
    var unitIndex = 0

    while (size >= 1024 && unitIndex < units.lastIndex) {
        size /= 1024
        unitIndex++
    }

    return if (size % 1.0 == 0.0) {
        String.format(Locale.US, "%.0f %s", size, units[unitIndex])
    } else {
        String.format(Locale.US, "%.1f %s", size, units[unitIndex])
    }
}

fun Double.roundTo(decimals: Int): Double {
    require(decimals >= 0) { "Decimal places must be non-negative" }
    val factor = 10.0.pow(decimals)
    return round(this * factor) / factor
}