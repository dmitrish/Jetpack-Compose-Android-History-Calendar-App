package com.coroutines.thisdayinhistory.ui.screens.uitheme


import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.coroutines.thisdayinhistory.R
import com.coroutines.thisdayinhistory.ui.previewProviders.ThisDayInHistoryThemeEnumProvider
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryTheme
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryThemeEnum
import com.coroutines.thisdayinhistory.ui.viewmodels.ISettingsViewModel
import com.coroutines.thisdayinhistory.ui.viewmodels.SettingsViewModelMock
import androidx.compose.ui.util.lerp
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Suppress("LongMethod", "LongParameterList")
fun ThemeScreen(
    modifier: Modifier = Modifier,
    viewModel: ISettingsViewModel
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        val themeState by viewModel.appConfigurationState.collectAsState()

        val pagerState = rememberPagerState(pageCount = { 3 })
        var tintColor = Color.Unspecified

        LaunchedEffect(key1 = true) {
            when (themeState.appTheme) {
                ThisDayInHistoryThemeEnum.Auto -> pagerState.scrollToPage(page = 0)
                ThisDayInHistoryThemeEnum.Light -> pagerState.scrollToPage(page = 1)
                ThisDayInHistoryThemeEnum.Dark -> pagerState.scrollToPage(page = 2)
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
                HorizontalPager(state = pagerState) { page ->
                    LaunchedEffect(pagerState) {
                        // Collect from the pager state a snapshotFlow reading the currentPage
                        snapshotFlow { pagerState.currentPage }.collect { item ->
                            when (item) {
                                0 -> {
                                    viewModel.setAppTheme(ThisDayInHistoryThemeEnum.Auto)
                                    tintColor = Color.White
                                }

                                1 -> {
                                    viewModel.setAppTheme(ThisDayInHistoryThemeEnum.Light)
                                    tintColor = Color.White
                                }

                                2 -> {
                                    viewModel.setAppTheme(ThisDayInHistoryThemeEnum.Dark)
                                    tintColor = Color.White
                                }
                            }
                        }
                    }
                    Column(
                        Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(0.dp, 50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center

                    ) {
                        // Our page content
                        when (page) {
                            0 -> {
                                SetUiTheme(
                                    viewModel,
                                    R.drawable.sun_68,
                                    ThisDayInHistoryThemeEnum.Auto,
                                    tintColor,
                                    "Auto - General Phone Theme",
                                    "Light Theme"
                                )
                            }

                            1 -> {
                                SetUiTheme(
                                    viewModel,
                                    R.drawable.sun_68,
                                    ThisDayInHistoryThemeEnum.Light,
                                    tintColor,
                                    "Light",
                                    "Light Theme"
                                )

                            }

                            2 -> {
                                /*SetUiTheme(viewModel,
                            R.drawable.moon,
                            HistoryCatThemeEnum.Dark,
                            tintColor,
                            "Dark",
                            "Dark Theme")*/
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.moon),
                                        "Dark Theme",
                                        modifier = Modifier
                                            .size(150.dp)
                                            .padding(30.dp, 35.dp)
                                            .clickable {
                                                viewModel.setAppTheme(ThisDayInHistoryThemeEnum.Dark)
                                            }
                                    )
                                    Text("Dark", textAlign = TextAlign.Center)
                                }
                            }
                        }
                    }
                }
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 18.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(6.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Suppress("LongParameterList")
private fun SetUiTheme(
    viewModel: ISettingsViewModel,
    @DrawableRes painterResourceId: Int,
    historyCatThemeEnum: ThisDayInHistoryThemeEnum,
    @ColorRes tintColor: Color,
    text: String,
    iconContentDescription: String,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(id = painterResourceId),
            contentDescription = iconContentDescription,
            modifier = Modifier
                .size(150.dp)
                .padding(30.dp, 30.dp)
                .clickable {
                    viewModel.setAppTheme(historyCatThemeEnum)
                },
            tint = tintColor
        )
        Text(text)
    }
}

@Preview
@Composable
fun PreviewThemeScreen(@PreviewParameter(ThisDayInHistoryThemeEnumProvider::class) historyCatThemeEnum: ThisDayInHistoryThemeEnum){

    val settingsViewModel = SettingsViewModelMock(historyCatThemeEnum)

    ThisDayInHistoryTheme(
        settingsViewModel
    ) {
        val appThemeColor = MaterialTheme.colorScheme.background
        Surface(
            modifier = Modifier.background(appThemeColor)
        ) {
            ThemeScreen(viewModel = settingsViewModel)
        }
    }
}