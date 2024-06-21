package com.coroutines.thisdayinhistory.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.coroutines.thisdayinhistory.R
import com.coroutines.thisdayinhistory.ui.state.AppConfigurationState
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryTheme
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryThemeEnum
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.platform.LocalContext
import com.coroutines.thisdayinhistory.ui.constants.CAT_LOGO_HEADER_TEXT_TAG
import com.coroutines.thisdayinhistory.ui.previewProviders.AppConfigurationStateProvider
import com.coroutines.thisdayinhistory.ui.viewmodels.SettingsViewModelMock

@Composable
fun CatLogo(settings: AppConfigurationState, includeText: Boolean = false) {
    val catLogoDrawable =
        if (settings.appTheme == ThisDayInHistoryThemeEnum.Dark || (settings.appTheme == ThisDayInHistoryThemeEnum.Auto && isSystemInDarkTheme())) R.drawable.cat_logo_for_dark_theme
        else R.drawable.cat_logo_for_light_theme

    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .placeholder(catLogoDrawable)
                .data(data = catLogoDrawable)
                .apply(block = fun ImageRequest.Builder.() {
                    crossfade(true)
                }).build()
        ),
        contentDescription = null,
        contentScale = ContentScale.Inside,
        modifier = Modifier
            .testTag(catLogoDrawable.toString())
            .fillMaxWidth()
            .size(175.dp)
            .padding(40.dp)
    )
    if (includeText) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )
        Text(
            "History Cat",
            modifier = Modifier.testTag(CAT_LOGO_HEADER_TEXT_TAG),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
            style = MaterialTheme.typography.headlineSmall,
        )
    }
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(5.dp)
    )
}



@Composable
@Preview()
fun CatLogoPreview(
    @PreviewParameter(
        AppConfigurationStateProvider::class,
        limit = 2) appConfigurationState: AppConfigurationState
) {
    val settingsViewModel = SettingsViewModelMock(appConfigurationState.appTheme)
    ThisDayInHistoryTheme(
        settingsViewModel
    ) {
        val appThemeColor = MaterialTheme.colorScheme.background
        Surface(
            modifier = Modifier.background(appThemeColor)
        ) {
            CatLogo(settings = appConfigurationState)
        }
    }
}