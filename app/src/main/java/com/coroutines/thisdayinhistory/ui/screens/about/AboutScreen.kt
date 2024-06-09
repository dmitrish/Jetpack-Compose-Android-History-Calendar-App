package com.coroutines.thisdayinhistory.ui.screens.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coroutines.data.models.LangEnum
import com.coroutines.data.models.Languages
import com.coroutines.thisdayinhistory.components.ScreenPlaceholder
import com.coroutines.thisdayinhistory.ui.components.CatLogo
import com.coroutines.thisdayinhistory.ui.constants.ABOUT_SCREEN_COLUMN_TAG
import com.coroutines.thisdayinhistory.ui.constants.ABOUT_SCREEN_TEXT_TAG
import com.coroutines.thisdayinhistory.ui.previewProviders.LanguageEnumProvider
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryTheme
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryThemeEnum
import com.coroutines.thisdayinhistory.ui.viewmodels.ISettingsViewModel
import com.coroutines.thisdayinhistory.ui.viewmodels.SettingsViewModelMock

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    viewModel: ISettingsViewModel = SettingsViewModelMock()
) {

    val about = Languages.from(
        viewModel.appConfigurationState.value.appLanguage.langId)?.appDescription
        ?: Languages.ENGLISH.appDescription
    val settings by viewModel.appConfigurationState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .testTag(ABOUT_SCREEN_COLUMN_TAG)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.height(30.dp))
        CatLogo(settings)
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = about,
            modifier = Modifier
                .testTag(ABOUT_SCREEN_TEXT_TAG)
                .padding(30.dp, 10.dp)
                .verticalScroll(rememberScrollState()),
            color = MaterialTheme.colorScheme.onBackground,
            lineHeight = 25.sp,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}


@Composable
@Preview()
fun AboutScreenPreview(@PreviewParameter(LanguageEnumProvider::class, limit = 11) langEnum: LangEnum) {
    val settingsViewModel = SettingsViewModelMock(ThisDayInHistoryThemeEnum.Dark)
    settingsViewModel.setAppLanguage(langEnum)
    ThisDayInHistoryTheme(
        settingsViewModel
    ) {
        val appThemeColor = MaterialTheme.colorScheme.background
        Surface(
            modifier = Modifier.background(appThemeColor)
        ) {
            AboutScreen(
                viewModel = settingsViewModel,
            )
        }
    }
}
