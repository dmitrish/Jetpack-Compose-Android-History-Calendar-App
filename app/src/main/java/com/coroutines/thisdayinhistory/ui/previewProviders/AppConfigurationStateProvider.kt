package com.coroutines.thisdayinhistory.ui.previewProviders

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.coroutines.data.models.LangEnum
import com.coroutines.thisdayinhistory.ui.state.AppConfigurationState
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryThemeEnum


class AppConfigurationStateProvider : PreviewParameterProvider<AppConfigurationState> {
    override val values = listOf(
        AppConfigurationState(
            isLoading = false,
            isOnboarded = true,
            useDynamicColors = false,
            appTheme = ThisDayInHistoryThemeEnum.Dark,
            appLanguage = LangEnum.ENGLISH,
            deviceLanguage = "en"),
        AppConfigurationState(
            isLoading = false,
            isOnboarded = true,
            useDynamicColors = false,
            appTheme = ThisDayInHistoryThemeEnum.Light,
            appLanguage = LangEnum.ENGLISH,
            deviceLanguage = "en"),
        AppConfigurationState(
            isLoading = true,
            isOnboarded = true,
            useDynamicColors = false,
            appTheme = ThisDayInHistoryThemeEnum.Dark,
            appLanguage = LangEnum.ENGLISH,
            deviceLanguage = "en"),
        AppConfigurationState(
            isLoading = true,
            isOnboarded = true,
            useDynamicColors = false,
            appTheme = ThisDayInHistoryThemeEnum.Light,
            appLanguage = LangEnum.ENGLISH,
            deviceLanguage = "en"),

        ).asSequence()
}
