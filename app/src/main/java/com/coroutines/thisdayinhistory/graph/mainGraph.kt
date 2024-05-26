package com.coroutines.thisdayinhistory.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.mainGraph(
    navController: NavController
) {
    navigation(startDestination = MainNavOption.HistoryScreen.name, route = NavRoutes.MainRoute.name) {

        composable(MainNavOption.HistoryScreen.name) {
           //todo History Screen Goes here
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
            //todo  Language Screen goes here
        }

        composable(
            MainNavOption.ThemeScreen.name
        ) {
            //todo Theme Screen
        }

        composable(
            MainNavOption.AboutScreen.name
        ) {
            //todo About Screen
        }
    }
}