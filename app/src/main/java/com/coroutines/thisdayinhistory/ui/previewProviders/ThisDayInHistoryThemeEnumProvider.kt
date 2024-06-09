package com.coroutines.thisdayinhistory.ui.previewProviders

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryThemeEnum


class ThisDayInHistoryThemeEnumProvider : PreviewParameterProvider<ThisDayInHistoryThemeEnum> {
    override val values = listOf(
        ThisDayInHistoryThemeEnum.Dark,
        ThisDayInHistoryThemeEnum.Light
    ).asSequence()
}