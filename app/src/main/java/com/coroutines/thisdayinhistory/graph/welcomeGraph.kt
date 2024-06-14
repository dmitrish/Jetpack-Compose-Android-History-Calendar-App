package com.coroutines.thisdayinhistory.graph

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navigation
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.coroutines.thisdayinhistory.ui.screens.language.LanguageScreen
import com.coroutines.thisdayinhistory.ui.screens.welcome.WelcomeScreen
import com.coroutines.thisdayinhistory.ui.viewmodels.ISettingsViewModel
import com.coroutines.thisdayinhistory.ui.viewmodels.WelcomeViewModelMock

const val langPrompt = "prompt"
fun NavGraphBuilder.introGraph(navController: NavController, settingsViewModel: ISettingsViewModel) {
    navigation(startDestination = IntroNavOption.WelcomeScreen.name, route = NavRoutes.IntroRoute.name) {
        composable(IntroNavOption.WelcomeScreen.name){
            val appConfigState by settingsViewModel.appConfigurationState.collectAsStateWithLifecycle()
            WelcomeScreen(navController, appConfigState)
        }
        /*composable(IntroNavOption.LanguagesScreen.name){
            LanguageScreen(navController = navController, viewModel = settingsViewModel)
        }*/

        composable(IntroNavOption.LanguagesScreen.name + "/{$langPrompt}",
            arguments = listOf(navArgument(langPrompt) { type = NavType.StringType })){ backStackEntry ->
            val prompt = backStackEntry.arguments?.getString(langPrompt) ?: "Please choose your language"
            LanguageScreen(
                navController= navController,
                viewModel = settingsViewModel,
                languagePrompt =  prompt
            )
        }
    }
}