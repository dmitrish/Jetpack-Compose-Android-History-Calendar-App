package com.coroutines.thisdayinhistory

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.coroutines.thisdayinhistory.graph.AppNavHost
import com.coroutines.thisdayinhistory.ui.state.AppConfigurationState
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryTheme
import com.coroutines.thisdayinhistory.ui.viewmodels.ISettingsViewModel

@Composable
fun MainContent(settingsViewModel: ISettingsViewModel, appConfigState: AppConfigurationState,){
    val navController = rememberNavController()
    ThisDayInHistoryTheme(
        viewModel = settingsViewModel
    ) {
        val appThemeColor = MaterialTheme.colorScheme.background
        Surface(
            modifier = Modifier.background(appThemeColor)
        ) {
            AppNavHost(
                navController = navController,
                settingsViewModel = settingsViewModel,
                isOnboarded = false
            )
        }
    }
}