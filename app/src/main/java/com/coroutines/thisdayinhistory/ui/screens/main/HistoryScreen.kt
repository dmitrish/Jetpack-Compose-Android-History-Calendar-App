package com.coroutines.thisdayinhistory.ui.screens.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.coroutines.thisdayinhistory.components.ScreenPlaceholder
import com.coroutines.thisdayinhistory.drawer.AppNavigationDrawerWithContent

@Composable
fun HistoryScreen(
    navController: NavController,
    modifier: Modifier = Modifier
){
   AppNavigationDrawerWithContent(navController = navController ) {
       ScreenPlaceholder(text = "Home Screen Placeholder")
   }
}