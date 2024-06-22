package com.coroutines.thisdayinhistory.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.coroutines.data.models.HistoricalEvent
import com.coroutines.thisdayinhistory.components.ScreenPlaceholder
import com.coroutines.thisdayinhistory.drawer.AppNavigationDrawerWithContent
import com.coroutines.thisdayinhistory.ui.viewmodels.ISettingsViewModel
import com.coroutines.thisdayinhistory.R
import com.coroutines.thisdayinhistory.ui.viewmodels.HistoryViewModelMock

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    settingsViewModel : ISettingsViewModel
){

   AppNavigationDrawerWithContent(
       navController = navController,
       settingsViewModel = settingsViewModel
   ) {
       val listState = rememberLazyListState()
       val historyViewModel = HistoryViewModelMock()
       val data = historyViewModel.historyData

       LazyColumn(
           state = listState,
           contentPadding = PaddingValues(bottom = 65.dp),
           verticalArrangement = Arrangement.Top
       ) {
           item { Spacer(Modifier.height(8.dp)) }

           items(data) { item: HistoricalEvent ->
               HistoryListItem(
                   historyEvent = item,
                   onClick = {},
                   onImageClick = {},
                   onShare = {},
               )
           }
           item { Spacer(Modifier.height(20.dp)) }
       }
   }
}