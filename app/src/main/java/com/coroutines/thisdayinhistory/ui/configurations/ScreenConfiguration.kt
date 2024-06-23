package com.coroutines.thisdayinhistory.ui.configurations

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import com.coroutines.thisdayinhistory.ui.theme.AppThemeLocal
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryThemeEnum

data class ScreenConfiguration(val localAppTheme: AppThemeLocal){
    val isDark = localAppTheme.historyCatThemeEnum == ThisDayInHistoryThemeEnum.Dark
    val isLargeWidth = localAppTheme.windowSizeClass.widthSizeClass > WindowWidthSizeClass.Medium
}
