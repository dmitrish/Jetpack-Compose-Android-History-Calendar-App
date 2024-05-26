package com.coroutines.thisdayinhistory.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import androidx.navigation.compose.composable

fun NavGraphBuilder.introGraph(navController: NavController) {
    navigation(startDestination = IntroNavOption.WelcomeScreen.name, route = NavRoutes.IntroRoute.name) {
        composable(IntroNavOption.WelcomeScreen.name){
           //todo - Welcome Screen goes here

        }
        composable(IntroNavOption.LanguagesScreen.name){
            //todo - Languages Screen goes here
        }
    }
}