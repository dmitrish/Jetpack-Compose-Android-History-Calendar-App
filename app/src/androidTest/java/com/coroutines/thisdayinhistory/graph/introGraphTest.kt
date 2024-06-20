package com.coroutines.thisdayinhistory.graph


import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.coroutines.thisdayinhistory.ui.screens.language.LanguageScreen
import com.coroutines.thisdayinhistory.ui.screens.welcome.WelcomeScreen
import com.coroutines.thisdayinhistory.ui.viewmodels.ISettingsViewModel
import com.coroutines.thisdayinhistory.ui.viewmodels.IWelcomeViewModel


fun NavGraphBuilder.introGraphTest(navController: NavController, viewModel: ISettingsViewModel, welcomeViewModel: IWelcomeViewModel) {
    navigation(startDestination = IntroNavOption.WelcomeScreen.name, route = NavRoutes.IntroRoute.name) {
        composable(IntroNavOption.WelcomeScreen.name){
            val appConfState = viewModel.appConfigurationState.collectAsState().value
            WelcomeScreen(navController, appConfState, welcomeViewModel)
        }
        composable(IntroNavOption.LanguagesScreen.name + "/{prompt}"){ backStackEntry ->
            val prompt = backStackEntry.arguments?.getString("prompt") ?: "Please choose your language"
            LanguageScreen(navController= navController,
                viewModel = viewModel,
                languagePrompt =  prompt)
        }
    }
}