package com.coroutines.thisdayinhistory.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.coroutines.thisdayinhistory.ui.screens.about.AboutScreen
import com.coroutines.thisdayinhistory.ui.screens.language.LanguageScreen
import com.coroutines.thisdayinhistory.ui.screens.main.HistoryScreen
import com.coroutines.thisdayinhistory.ui.screens.uitheme.ThemeScreen

fun NavGraphBuilder.mainGraph(
    navController: NavController
) {
    navigation(startDestination = MainNavOption.HistoryScreen.name, route = NavRoutes.MainRoute.name) {

        composable(MainNavOption.HistoryScreen.name) {
           HistoryScreen(navController = navController)
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
           LanguageScreen()
        }

        composable(
            MainNavOption.ThemeScreen.name
        ) {
           ThemeScreen()
        }

        composable(
            MainNavOption.AboutScreen.name
        ) {
           AboutScreen()
        }
    }
}