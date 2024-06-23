package com.coroutines.thisdayinhistory.ui.screens.main

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.coroutines.data.models.HistoricalEvent
import com.coroutines.thisdayinhistory.components.ScreenPlaceholder
import com.coroutines.thisdayinhistory.drawer.AppNavigationDrawerWithContent
import com.coroutines.thisdayinhistory.ui.viewmodels.ISettingsViewModel
import com.coroutines.thisdayinhistory.R
import com.coroutines.thisdayinhistory.ui.state.DataRequestState
import com.coroutines.thisdayinhistory.ui.state.RequestCategory
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
       val viewModel = HistoryViewModelMock()
       val uiState by viewModel.uiState.collectAsStateWithLifecycle()
       val dataRequestState = uiState.dataRequestState
       val option = uiState.selectedCategory
       val categories = uiState.catsByLanguage.getCategories().values.toList()

       AnimatedContent(targetState = dataRequestState,
           transitionSpec = {
               if (dataRequestState == DataRequestState.CompletedSuccessfully(RequestCategory.Option)) {
                   (slideInHorizontally (animationSpec = tween(durationMillis = 300))
                   { width -> width }
                           + fadeIn()).togetherWith(slideOutHorizontally
                   { width -> -width } + fadeOut())
               } else {
                   fadeIn(animationSpec = tween(200)) togetherWith
                           fadeOut(animationSpec = tween(200)) using
                           SizeTransform { initialSize, targetSize ->
                               if (targetState == DataRequestState.CompletedSuccessfully()) {
                                   keyFramesToTargetState(targetSize)
                               } else {
                                   keyFramesToNonTargetState(initialSize)
                               }
                           }
               }
           }, label = "") { targetState ->
           when {
               targetState is DataRequestState.Started
               -> HistoryScreenLoading(viewModel)
               targetState is DataRequestState.CompletedWithError
               -> HistoryScreenError(targetState.errorMessage)
               targetState is DataRequestState.CompletedSuccessfully
               -> {
                   Column(Modifier.fillMaxWidth()) {
                       HistoryViewCategoryTabs(
                           categories = categories,
                           option,
                           onCategorySelected = viewModel::onCategoryChanged,
                           tabItemsPadding = 10.dp,
                           modifier = Modifier
                               .fillMaxWidth()
                               .background(MaterialTheme.colorScheme.background)
                       )
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
           }
       }
   }
}

private fun keyFramesToNonTargetState(initialSize: IntSize) = keyframes {
    IntSize(
        initialSize.width / 5,
        initialSize.height / 5
    ) at 60
    durationMillis = 70
    IntSize(
        initialSize.width / 3,
        initialSize.height / 3
    ) at 130
    durationMillis = 70
    IntSize(
        initialSize.width / 2,
        initialSize.height / 2
    ) at 150
    durationMillis = 70
}


private fun keyFramesToTargetState(targetSize: IntSize) = keyframes {
    // Expand horizontally first.
    IntSize(0, 0) at 0
    durationMillis = 50
    IntSize(
        targetSize.width / 5,
        targetSize.height / 5
    ) at 60
    durationMillis = 50
    IntSize(
        targetSize.width / 3,
        targetSize.height / 3
    ) at 100
    durationMillis = 50
    IntSize(
        targetSize.width / 2,
        targetSize.height / 2
    ) at 150
    durationMillis = 50
}