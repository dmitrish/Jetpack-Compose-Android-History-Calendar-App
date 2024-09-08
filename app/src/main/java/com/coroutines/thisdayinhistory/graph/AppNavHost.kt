package com.coroutines.thisdayinhistory.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.coroutines.thisdayinhistory.ui.viewmodels.IHistoryViewModel
import com.coroutines.thisdayinhistory.ui.viewmodels.ISettingsViewModel

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    settingsViewModel: ISettingsViewModel,
    isOnboarded: Boolean,
    historyViewModel: IHistoryViewModel
){
    NavHost(
        navController,
        startDestination = if (isOnboarded) NavRoutes.MainRoute.name else NavRoutes.IntroRoute.name
    ) {
        introGraph(navController, settingsViewModel)
        mainGraph(navController, settingsViewModel, historyViewModel)
    }
}



