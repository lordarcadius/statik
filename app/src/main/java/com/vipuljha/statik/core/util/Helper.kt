package com.vipuljha.statik.core.util

object Helper {
    fun formatFreq(freq: String): String {
        return try {
            val khz = freq.toLong()
            val mhz = khz / 1000
            val ghz = mhz / 1000.0
            String.format("%.2f GHz", ghz)
        } catch (e: Exception) {
            "N/A"
        }
    }
}