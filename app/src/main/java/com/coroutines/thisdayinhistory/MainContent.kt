package com.coroutines.thisdayinhistory

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.rememberNavController
import com.coroutines.api.wiki.WikiMediaApiService
import com.coroutines.api.wiki.WikiMediaApiServiceImpl
import com.coroutines.data.converters.JsonConverterService
import com.coroutines.data.models.LangEnum
import com.coroutines.thisdayinhistory.components.rememberSystemUiController
import com.coroutines.thisdayinhistory.graph.AppNavHost
import com.coroutines.thisdayinhistory.ui.state.AppConfigurationState
import com.coroutines.thisdayinhistory.ui.theme.AppThemeLocal
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryTheme
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryThemeEnum
import com.coroutines.thisdayinhistory.ui.viewmodels.HistoryViewModel
import com.coroutines.thisdayinhistory.ui.viewmodels.IHistoryViewModel
import com.coroutines.thisdayinhistory.ui.viewmodels.ISettingsViewModel
import com.coroutines.thisdayinhistory.uimodels.HistoryCalendar
import com.coroutines.thisdayinhistory.uimodels.HistoryDataMap
import com.coroutines.usecase.HistoryDataStandardUseCase
import com.coroutines.usecase.IHistoryDataStandardUseCase

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
val LocalAppTheme = compositionLocalOf {
    AppThemeLocal(ThisDayInHistoryThemeEnum.Auto, WindowSizeClass.calculateFromSize(DpSize.Zero))
}
@Composable
fun MainContent(
    settingsViewModel: ISettingsViewModel,
    appConfigState: AppConfigurationState,
    historyViewModel: IHistoryViewModel,
    windowSize: WindowSizeClass
){
    val navController = rememberNavController()
    ThisDayInHistoryTheme(
        viewModel = settingsViewModel
    ) {
        val appThemeColor = MaterialTheme.colorScheme.background
        Surface(
            modifier = Modifier.background(appThemeColor)
        ) {
            val systemUiController = rememberSystemUiController()
            systemUiController.setSystemBarsColor(
                color = appThemeColor,
                isNavigationBarContrastEnforced = false
            )

            val viewModelStoreOwner =
                checkNotNull(LocalViewModelStoreOwner.current) {
                    "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
                }

            val localAppTheme =
                AppThemeLocal(appConfigState.appTheme, windowSize)



            CompositionLocalProvider(
                LocalViewModelStoreOwner provides viewModelStoreOwner,
                LocalAppTheme provides localAppTheme
            ) {
                AppNavHost(
                    navController = navController,
                    settingsViewModel = settingsViewModel,
                    historyViewModel = historyViewModel,
                    isOnboarded = appConfigState.isOnboarded
                )
            }
        }
    }
}