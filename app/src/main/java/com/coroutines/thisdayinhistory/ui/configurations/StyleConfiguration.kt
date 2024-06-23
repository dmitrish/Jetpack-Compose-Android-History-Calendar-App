package com.coroutines.thisdayinhistory.ui.configurations

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

data class StyleConfiguration(
    private val screenConfiguration: ScreenConfiguration,
    private val typography: Typography,
    private val screen: String,){

    val contrastAgainstColor = Color.LightGray
    val isDark = screenConfiguration.isDark
    val bodyTypography =
        if (screenConfiguration.isLargeWidth) typography.bodyLarge else typography.bodyMedium
    val lineHeight =
        if (screenConfiguration.isLargeWidth) 30.sp else 24.sp
}