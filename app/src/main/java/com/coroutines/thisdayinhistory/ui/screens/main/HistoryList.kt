package com.coroutines.thisdayinhistory.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.coroutines.data.models.HistoricalEvent
import com.coroutines.thisdayinhistory.components.NAV_ARGUMENT_DOMINANT_COLOR
import com.coroutines.thisdayinhistory.components.NAV_ARGUMENT_HISTORY_EVENT
import com.coroutines.thisdayinhistory.graph.MainNavOption
import com.coroutines.thisdayinhistory.ui.components.DominantColorState
import com.coroutines.thisdayinhistory.ui.components.MinContrastOfPrimaryVsSurface
import com.coroutines.thisdayinhistory.ui.components.ScrollToTopButton
import com.coroutines.thisdayinhistory.ui.components.contrastAgainst
import com.coroutines.thisdayinhistory.ui.components.rememberDominantColorState
import com.coroutines.thisdayinhistory.ui.theme.BabyPowder
import com.coroutines.thisdayinhistory.ui.viewmodels.HistoryViewModelMock
import com.coroutines.thisdayinhistory.ui.viewmodels.IHistoryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ModalBottomSheetValue


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Suppress("LongMethod")
@Composable
fun HistoryEventList(
    viewModel: IHistoryViewModel,
    windowSizeClass: WindowSizeClass,
    navController: NavController,
    onItemImageStateChanged: (readyState: Boolean) -> Unit
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedCategory = uiState.selectedCategory
    val previousCategory = uiState.previousCategory
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val data = viewModel.historyData

    val backgroundColor = MaterialTheme.colorScheme.background
    val dominantColorState = rememberDominantColorState { color ->
        color.contrastAgainst(backgroundColor) >= MinContrastOfPrimaryVsSurface
    }

    val skipHalfExpanded by remember { mutableStateOf(true) }
    val modalBottomSheetState = androidx.compose.material.rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = skipHalfExpanded
    )

    var showModalBottomSheet by remember { mutableStateOf(false) }
    if (modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(Unit) {
            onDispose {
                showModalBottomSheet = false
                onItemImageStateChanged (false)
            }
        }
    }


    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topEnd = 35.dp, topStart = 35.dp),
        sheetBackgroundColor = Color.Transparent,
        scrimColor = Color.Transparent,
        sheetElevation = 0.dp,
        sheetContent = {
            BottomSheetContent(viewModel, showModalBottomSheet, dominantColorState)
        }
    ) {
        AnimatedContent(
            targetState = Pair<String, String>(
                selectedCategory,
                previousCategory
            ),
            transitionSpec = {
                if (selectedCategory != previousCategory) {
                    (slideInHorizontally(
                        animationSpec = tween(
                            durationMillis = 200
                        )
                    )
                    { width -> width }
                            + fadeIn()).togetherWith(slideOutHorizontally
                    { width -> -width } + fadeOut())
                } else {
                    fadeIn(animationSpec = tween(200)) togetherWith
                            fadeOut(animationSpec = tween(200))
                }
            }, label = ""
        ) {
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(bottom = 65.dp),
                verticalArrangement = Arrangement.Top
            ) {
                item { Spacer(Modifier.height(8.dp)) }

                items(data) { item: HistoricalEvent ->
                    HistoryListItem(
                        historyEvent = item,
                        //windowSizeClass = windowSizeClass,
                        onClick = { selectedEvent ->
                            viewModel.selectedItem = selectedEvent
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                NAV_ARGUMENT_HISTORY_EVENT, selectedEvent
                            )
                            navigateToDetail(
                                backgroundColor,
                                coroutineScope,
                                viewModel,
                                dominantColorState,
                                navController
                            )
                        },
                        onImageClick = { selectedEvent ->
                            viewModel.selectedItem = selectedEvent
                            coroutineScope.launch {
                                if (viewModel.selectedItem.imageUrl.isNotEmpty()) {
                                    dominantColorState.updateColorsFromImageUrl(viewModel.selectedItem.imageUrl)
                                } else {
                                    dominantColorState.reset()
                                }
                                showModalBottomSheet = true
                                onItemImageStateChanged(true)
                                modalBottomSheetState.show()
                            }
                        },
                        onShare = {
                        }
                    )
                }
                item { Spacer(Modifier.height(20.dp)) }
            }


            val showButton by remember {
                derivedStateOf {
                    listState.firstVisibleItemIndex > 0
                }
            }

            viewModel.isScrolled.value = showButton

            AnimatedVisibility(
                visible = showButton,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                ScrollToTopButton(onClick = {
                    coroutineScope.launch {
                        listState.scrollToItem(0)
                    }
                })
            }
        }
    }
}

private fun navigateToDetail(
    backgroundColor: Color,
    coroutineScope: CoroutineScope,
    viewModel: IHistoryViewModel,
    dominantColorState: DominantColorState,
    navController: NavController
) {

    if (backgroundColor != BabyPowder) {
        coroutineScope.launch {
            val job = launch {
                if (viewModel.selectedItem.imageUrl.isNotEmpty()) {
                    dominantColorState.updateColorsFromImageUrl(viewModel.selectedItem.imageUrl)
                } else {
                    dominantColorState.reset()
                }
            }
            job.join()
            navController.currentBackStackEntry?.savedStateHandle?.set(
                NAV_ARGUMENT_DOMINANT_COLOR,
                dominantColorState.color.value.toString()
            )

            navController.navigate(MainNavOption.DetailScreen.name) {
                popUpTo(MainNavOption.HistoryScreen.name)
            }
        }
    } else {
        navController.navigate(MainNavOption.DetailScreen.name) {
            popUpTo(MainNavOption.HistoryScreen.name)
        }
    }
}

class ViewModelProvider : PreviewParameterProvider<IHistoryViewModel> {
    override val values = buildList<IHistoryViewModel> {
        HistoryViewModelMock()
    }.asSequence()
}
class WindowSizeClassProvider : PreviewParameterProvider<WindowSizeClass> {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override val values = buildList<WindowSizeClass> {
        WindowSizeClass.calculateFromSize(DpSize.Unspecified)
    }.asSequence()
}

/*
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HistoryEventListPreview(
    @PreviewParameter(HistoryCatThemeEnumProvider::class) historyCatThemeEnum: HistoryCatThemeEnum
) {
    val viewModel = HistoryViewModelMock()
    val windowSizeClass = WindowSizeClass.calculateFromSize(DpSize.Zero)
    val navController = rememberNavController()
    val localAppTheme = AppThemeLocal(HistoryCatThemeEnum.Dark, windowSizeClass)
    val settingsViewModel = SettingsViewModelMock(historyCatThemeEnum)

    HistoryCatTheme(
        settingsViewModel
    ) {
        val appThemeColor = MaterialTheme.colorScheme.background
        Surface(
            modifier = Modifier.background(appThemeColor)
        ) {
            CompositionLocalProvider(
                LocalAppTheme provides localAppTheme
            ) {
                HistoryEventList(
                    viewModel = viewModel,
                    windowSizeClass = windowSizeClass,
                    navController = navController,
                    onItemImageStateChanged = { }
                )
            }
        }
    }
}*/