package com.coroutines.thisdayinhistory

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.coroutines.thisdayinhistory.graph.AppNavHost
import com.coroutines.thisdayinhistory.ui.state.AppConfigurationState
import com.coroutines.thisdayinhistory.ui.viewmodels.ISettingsViewModel

@Composable
fun MainContent(settingsViewModel: ISettingsViewModel, appConfigState: AppConfigurationState,){
    val navController = rememberNavController()
    AppNavHost(
        navController = navController,
        settingsViewModel = settingsViewModel,
        isOnboarded = true
    )
}