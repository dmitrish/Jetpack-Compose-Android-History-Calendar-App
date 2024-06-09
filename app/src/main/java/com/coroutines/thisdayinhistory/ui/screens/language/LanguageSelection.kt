package com.coroutines.thisdayinhistory.ui.screens.language

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coroutines.data.models.LangEnum
import com.coroutines.thisdayinhistory.ui.constants.LANGUAGE_SELECTION_TEXT_TAG
import com.coroutines.thisdayinhistory.ui.previewProviders.ThisDayInHistoryThemeEnumProvider
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryTheme
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryThemeEnum
import com.coroutines.thisdayinhistory.ui.viewmodels.SettingsViewModelMock

@Composable
fun LanguageSelection(
    language: LangEnum,
    modifier: Modifier,
    onLangSelection: (language: LangEnum) -> Unit
)
{
    Text(modifier = modifier
        .testTag(LANGUAGE_SELECTION_TEXT_TAG)
        .sizeIn(
            minWidth = 48.dp,
            minHeight = 48.dp
        )
        .padding(0.dp, 10.dp)
        .semantics {
            contentDescription = language.langName
        }
        .clickable(enabled = true) {
            onLangSelection(language)
        },
        text = language.langNativeName,
        lineHeight = 20.sp,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
@Preview
fun LanguageSelectionPreview(
    @PreviewParameter(ThisDayInHistoryThemeEnumProvider::class)
    thisDayInHistoryThemeEnum: ThisDayInHistoryThemeEnum
) {

    val settingsViewModel = SettingsViewModelMock(thisDayInHistoryThemeEnum)

    ThisDayInHistoryTheme(
        settingsViewModel
    ) {
        val appThemeColor = MaterialTheme.colorScheme.background
        Surface(
            modifier = Modifier.background(appThemeColor)
        ) {

            LanguageSelection(language = LangEnum.GERMAN,
                modifier = Modifier, onLangSelection = {} )
        }
    }
}