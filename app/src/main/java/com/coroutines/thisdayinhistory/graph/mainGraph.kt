package com.coroutines.thisdayinhistory.graph

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.coroutines.thisdayinhistory.LocalAppTheme
import com.coroutines.thisdayinhistory.components.rememberSystemUiController
import com.coroutines.thisdayinhistory.ui.appbar.ScreenWithSecondaryAppBar
import com.coroutines.thisdayinhistory.ui.configurations.ScreenConfiguration
import com.coroutines.thisdayinhistory.ui.configurations.StyleConfiguration
import com.coroutines.thisdayinhistory.ui.screens.about.AboutScreen
import com.coroutines.thisdayinhistory.ui.screens.detail.DetailScreen
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
            val systemUiController = rememberSystemUiController()
            val appBackgroundColor = MaterialTheme.colorScheme.background
            ScreenWithSecondaryAppBar(navController = navController) {
                DetailScreen(
                    modifier = Modifier,
                    navController = navController,
                    backHandler = {
                        systemUiController.setNavigationBarColor(appBackgroundColor)
                        navController.popBackStack()
                    },
                    darkThemeHandler = {
                        systemUiController.setNavigationBarColor(
                            color = Color.Transparent,
                            navigationBarContrastEnforced = false
                        )
                        systemUiController.setStatusBarColor(color = Color.Transparent)
                    },
                    styleConfiguration = StyleConfiguration(
                        ScreenConfiguration(LocalAppTheme.current),
                        MaterialTheme.typography,
                        "detail"
                    )
                )
            }
        }

        composable(
            MainNavOption.LanguagesScreen.name
        ) {
            ScreenWithSecondaryAppBar(navController = navController) {
                LanguageScreen(
                    navController = navController,
                    viewModel = settingsViewModel
                )
            }
        }

        composable(
            MainNavOption.ThemeScreen.name
        ) {
            ScreenWithSecondaryAppBar(navController = navController) {
                ThemeScreen(viewModel = settingsViewModel)
            }
        }

        composable(
            MainNavOption.AboutScreen.name
        ) {
            ScreenWithSecondaryAppBar(navController = navController) {
                AboutScreen(viewModel = settingsViewModel)
            }
        }
    }
}