package com.coroutines.thisdayinhistory.ui.viewmodels

import com.coroutines.data.models.LangEnum
import com.coroutines.thisdayinhistory.ui.state.AppConfigurationState
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryThemeEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ISettingsViewModel {

    val appConfigurationState: StateFlow<AppConfigurationState>
    fun setAppTheme(theme: ThisDayInHistoryThemeEnum)
    fun setAppLanguage(langEnum: LangEnum)
    fun setDeviceLanguage(language: String)
    fun setOnboarded()

    val aboutDescription: Flow<String>
}
