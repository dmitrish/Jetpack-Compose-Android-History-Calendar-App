package com.coroutines.thisdayinhistory.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import androidx.navigation.compose.composable
import com.coroutines.thisdayinhistory.ui.screens.language.LanguageScreen
import com.coroutines.thisdayinhistory.ui.screens.welcome.WelcomeScreen

fun NavGraphBuilder.introGraph(navController: NavController) {
    navigation(startDestination = IntroNavOption.WelcomeScreen.name, route = NavRoutes.IntroRoute.name) {
        composable(IntroNavOption.WelcomeScreen.name){
            WelcomeScreen()
        }
        composable(IntroNavOption.LanguagesScreen.name){
            LanguageScreen(navController = navController)
        }
    }
}