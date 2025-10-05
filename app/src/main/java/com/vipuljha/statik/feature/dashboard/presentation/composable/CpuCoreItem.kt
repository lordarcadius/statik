package com.vipuljha.statik.feature.dashboard.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vipuljha.statik.core.util.Helper.formatFreq
import com.vipuljha.statik.feature.dashboard.domain.model.PerCoreFreqModel

@Composable
fun CpuCoreItem(modifier: Modifier = Modifier, core: PerCoreFreqModel) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(formatFreq(core.currentFrequency))
        LinearProgressIndicator(
            progress = { core.currentFrequency.toFloat() / core.maxFrequency.toFloat() },
            modifier = modifier.fillMaxWidth(),
        )
    }
}