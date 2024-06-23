package com.coroutines.thisdayinhistory.ui.screens.main

import android.text.BidiFormatter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.coroutines.thisdayinhistory.R


@Composable
fun HistoryScreenError(errorMessage: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        val errorMessageFormatter: String =
            java.lang.String.format(
                stringResource(R.string.error_message_prefix),
                BidiFormatter.getInstance().unicodeWrap(errorMessage)
            )
        Text(errorMessageFormatter)
    }
}

/*
class ErrorTextProvider : PreviewParameterProvider<String> {
    override val values = listOf("Network not available", "Internal error").asSequence()
}
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview
@Composable
fun PreviewScreenError(@PreviewParameter(ErrorTextProvider::class) errorMessage:String){

    val localAppTheme = AppThemeLocal(
        HistoryCatThemeEnum.Dark,
        WindowSizeClass.calculateFromSize(DpSize.Zero)
    )
    val settingsViewModel = SettingsViewModelMock()
    settingsViewModel.setAppTheme(HistoryCatThemeEnum.Dark)
    HistoryCatTheme(
        settingsViewModel
    ) {
        val appThemeColor = MaterialTheme.colorScheme.background
        Surface(
            modifier = Modifier.background(appThemeColor)
        ) {
            CompositionLocalProvider(
                LocalAppTheme provides localAppTheme
            ) {
                ScreenError(errorMessage = errorMessage)
            }
        }
    }
}*/