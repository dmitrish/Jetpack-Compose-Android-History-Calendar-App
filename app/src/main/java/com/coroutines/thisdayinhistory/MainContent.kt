package com.coroutines.thisdayinhistory

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.coroutines.thisdayinhistory.graph.AppNavHost

@Composable
fun MainContent(){
    val navController = rememberNavController()
    AppNavHost(
        navController = navController,
        isOnboarded = true
    )
}