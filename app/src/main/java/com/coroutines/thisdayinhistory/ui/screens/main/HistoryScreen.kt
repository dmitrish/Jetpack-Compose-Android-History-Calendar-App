package com.coroutines.thisdayinhistory.ui.screens.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.coroutines.thisdayinhistory.components.ScreenPlaceholder
import com.coroutines.thisdayinhistory.drawer.AppNavigationDrawerWithContent
import com.coroutines.thisdayinhistory.ui.viewmodels.ISettingsViewModel

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    settingsViewModel : ISettingsViewModel

){
   AppNavigationDrawerWithContent(
       navController = navController,
       settingsViewModel =  settingsViewModel
   ) {
       ScreenPlaceholder(text = "Home Screen Placeholder")
   }
}