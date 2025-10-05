package com.vipuljha.statik.core.util

import android.os.Build
import com.vipuljha.statik.feature.dashboard.domain.model.PerCoreFreqModel
import java.io.File
import java.text.DecimalFormat
import java.util.Locale
import kotlin.math.ln
import kotlin.math.pow
import kotlin.random.Random
import kotlin.math.roundToInt

object Helper {

    private val decimalFormat by lazy { DecimalFormat("#.##") }
    fun formatFreq(freq: Long): String {
        return try {
            val khz = freq
            val mhz = khz / 1000
            val ghz = mhz / 1000.0
            String.format(Locale.US, "%.2f GHz", ghz)
        } catch (e: Exception) {
            "N/A"
        }
    }

    fun isEmulator(): Boolean {
        val checkBuildProps = (
                Build.FINGERPRINT.startsWith("generic", ignoreCase = true) ||
                        Build.FINGERPRINT.startsWith("unknown", ignoreCase = true) ||
                        Build.MODEL.contains("google_sdk", ignoreCase = true) ||
                        Build.MODEL.contains("Emulator", ignoreCase = true) ||
                        Build.MODEL.contains("Android SDK built for x86", ignoreCase = true) ||
                        Build.PRODUCT.contains("sdk_google", ignoreCase = true) ||
                        Build.PRODUCT.contains("google_sdk", ignoreCase = true) ||
                        Build.PRODUCT.contains("sdk", ignoreCase = true) ||
                        Build.PRODUCT.contains("vbox86p", ignoreCase = true) ||
                        Build.PRODUCT.contains("emulator", ignoreCase = true) ||
                        Build.PRODUCT.contains("simulator", ignoreCase = true) ||
                        Build.HARDWARE.contains("goldfish", ignoreCase = true) ||
                        Build.HARDWARE.contains("vbox", ignoreCase = true) ||
                        Build.HARDWARE.contains("qemu", ignoreCase = true) ||
                        Build.BRAND.startsWith("generic", ignoreCase = true) &&
                        Build.DEVICE.startsWith("generic", ignoreCase = true) ||
                        Build.MANUFACTURER.contains("Genymotion", ignoreCase = true)
                )

        val checkTestKeys = Build.TAGS?.contains("test-keys") == true
        return checkBuildProps || checkTestKeys
    }

    fun getFakeEmulatorFreqData(): List<PerCoreFreqModel> {
        val fakeCores = 8
        val fakeMin = 300000L   // 300 MHz
        val fakeMax = 4300000L  // 4.3 GHz

        return List(fakeCores) { index ->
            PerCoreFreqModel(
                core = "cpu$index",
                minFrequency = fakeMin,
                maxFrequency = fakeMax,
                currentFrequency = Random.nextLong(
                    fakeMin,
                    fakeMax
                )
            )
        }
    }

    fun readFile(path: String): String {
        return try {
            File(path).readText().trim()
        } catch (e: Exception) {
            "0"
        }
    }

    fun formatBytes(bytes: Long): String {
        if (bytes < 1024) return "$bytes B"

        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (ln(bytes.toDouble()) / ln(1024.0)).toInt()
        val value = bytes / 1024.0.pow(digitGroups.toDouble())

        return "${decimalFormat.format(value)} ${units[digitGroups]}"
    }

    fun formatPercentage(value: Float): String {
        return "${value.roundToInt()}%"
    }
}