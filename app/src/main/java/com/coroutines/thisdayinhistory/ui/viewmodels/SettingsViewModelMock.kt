package com.coroutines.thisdayinhistory.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coroutines.data.models.LangEnum
import com.coroutines.data.models.Languages
import com.coroutines.thisdayinhistory.ui.state.AppConfigurationState
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryThemeEnum
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

private data class SettingsViewModelStateMock(
    val isLoading: Boolean = true,
    val isOnboarded: Boolean = false,
    val useDynamicColors: Boolean = true,
    val appTheme: ThisDayInHistoryThemeEnum = ThisDayInHistoryThemeEnum.Dark,
    val appLanguage: LangEnum = LangEnum.ENGLISH,
    val deviceLanguage: String = ""
) {
    fun asActivityState() = AppConfigurationState(
        isLoading = isLoading,
        isOnboarded = isOnboarded,
        useDynamicColors = useDynamicColors,
        appTheme = appTheme,
        appLanguage = appLanguage,
        deviceLanguage = deviceLanguage
    )
}
class SettingsViewModelMock(
    historyCatThemeEnum: ThisDayInHistoryThemeEnum = ThisDayInHistoryThemeEnum.Auto
) : ViewModel(), ISettingsViewModel {

    private var _aboutDescription = MutableStateFlow(Languages.ENGLISH.appDescription)
    private val viewModelState = MutableStateFlow(value = SettingsViewModelStateMock())

    override val appConfigurationState = viewModelState
        .map { it.asActivityState().copy(appTheme = historyCatThemeEnum) }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = viewModelState.value.asActivityState()
        )

    override fun setAppTheme(theme: ThisDayInHistoryThemeEnum) {
        viewModelState.update { state ->
            state.copy(
                appTheme = state.appTheme
            )
        }
    }

    override fun setAppLanguage(langEnum: LangEnum) {
        viewModelState.update { state ->
            state.copy(
                appLanguage = langEnum
            )
        }
    }

    override fun setDeviceLanguage(language: String) {
        TODO("Not yet implemented")
    }

    override fun setOnboarded() {
        TODO("Not yet implemented")
    }

    override val aboutDescription: StateFlow<String>
        get() = _aboutDescription
}