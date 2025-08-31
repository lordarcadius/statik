package com.vipuljha.statik.feature.information

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppDetailScreen(modifier: Modifier = Modifier, packageName: String) {
    Text("App Detail Screen. Package Name: $packageName")
}