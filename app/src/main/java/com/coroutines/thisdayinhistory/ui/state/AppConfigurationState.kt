package com.coroutines.thisdayinhistory.ui.state

import com.coroutines.data.models.LangEnum
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryThemeEnum

data class AppConfigurationState(
    val isLoading: Boolean,
    val isOnboarded: Boolean,
    val useDynamicColors: Boolean,
    val appTheme: ThisDayInHistoryThemeEnum,
    val appLanguage: LangEnum,
    val deviceLanguage: String
)