package com.coroutines.thisdayinhistory.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import androidx.navigation.compose.composable
import com.coroutines.thisdayinhistory.ui.screens.language.LanguageScreen
import com.coroutines.thisdayinhistory.ui.screens.welcome.WelcomeScreen
import com.coroutines.thisdayinhistory.ui.viewmodels.ISettingsViewModel

fun NavGraphBuilder.introGraph(navController: NavController, settingsViewModel: ISettingsViewModel) {
    navigation(startDestination = IntroNavOption.WelcomeScreen.name, route = NavRoutes.IntroRoute.name) {
        composable(IntroNavOption.WelcomeScreen.name){
            WelcomeScreen()
        }
        composable(IntroNavOption.LanguagesScreen.name){
            LanguageScreen(navController = navController, viewModel = settingsViewModel)
        }
    }
}