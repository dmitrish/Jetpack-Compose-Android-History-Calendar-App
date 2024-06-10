package com.coroutines.thisdayinhistory.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.coroutines.thisdayinhistory.ui.screens.about.AboutScreen
import com.coroutines.thisdayinhistory.ui.screens.language.LanguageScreen
import com.coroutines.thisdayinhistory.ui.screens.main.HistoryScreen
import com.coroutines.thisdayinhistory.ui.screens.uitheme.ThemeScreen
import com.coroutines.thisdayinhistory.ui.viewmodels.ISettingsViewModel

fun NavGraphBuilder.mainGraph(
    navController: NavController,
    settingsViewModel : ISettingsViewModel
) {
    navigation(startDestination = MainNavOption.HistoryScreen.name, route = NavRoutes.MainRoute.name) {

        composable(MainNavOption.HistoryScreen.name) {
           HistoryScreen(navController = navController, settingsViewModel = settingsViewModel)
        }

        composable(
            MainNavOption.DetailScreen.name
        )
        { backStackEntry ->
            //todo Detail Screen goes here
        }

        composable(
            MainNavOption.LanguagesScreen.name
        ) {
           LanguageScreen(navController = navController, viewModel =  settingsViewModel)
        }

        composable(
            MainNavOption.ThemeScreen.name
        ) {
           ThemeScreen(viewModel  = settingsViewModel)
        }

        composable(
            MainNavOption.AboutScreen.name
        ) {
           AboutScreen(viewModel = settingsViewModel)
        }
    }
}