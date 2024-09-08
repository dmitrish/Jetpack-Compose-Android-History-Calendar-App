package com.coroutines.thisdayinhistory.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.coroutines.thisdayinhistory.components.CALENDAR_PAGE_COUNT
import com.coroutines.thisdayinhistory.ui.previewProviders.ThisDayInHistoryThemeEnumProvider
import com.coroutines.thisdayinhistory.ui.state.HistoryViewModelState
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryTheme
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryThemeEnum
import com.coroutines.thisdayinhistory.ui.viewmodels.IHistoryViewModel
import com.coroutines.thisdayinhistory.ui.viewmodels.SettingsViewModelMock


@OptIn(ExperimentalFoundationApi::class)
@Suppress("MagicNumber")
@Composable
fun BottomNavigationBarCalendar(
    historyViewModel: IHistoryViewModel,
    historyViewModelState: HistoryViewModelState
) {

    HistoryCatNavigationBar(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            var previousPage by remember { mutableStateOf(CALENDAR_PAGE_COUNT/2) }
            val pagerState = rememberPagerState(
                pageCount = { CALENDAR_PAGE_COUNT },
                initialPage = CALENDAR_PAGE_COUNT/2
            )
            LaunchedEffect(pagerState) {
                snapshotFlow { pagerState.currentPage }.collect { page ->
                    when {
                        page > previousPage -> historyViewModel.updateDate(1)
                        page < previousPage -> historyViewModel.updateDate(-1)
                    }
                    previousPage = page
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth(),
            ) { page ->
                with (historyViewModelState.selectedDate){
                    Text(
                        text = "$month $day",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().wrapContentWidth()
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun BottomNavigationBarCalendarPreview(
    @PreviewParameter(ThisDayInHistoryThemeEnumProvider::class) historyCatThemeEnum: ThisDayInHistoryThemeEnum
) {

    val settingsViewModel = SettingsViewModelMock(historyCatThemeEnum)

    ThisDayInHistoryTheme(
        settingsViewModel
    ) {
        val appThemeColor = MaterialTheme.colorScheme.background
        Surface(
            modifier = Modifier.background(appThemeColor)
        ) {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)),
                containerColor = MaterialTheme.colorScheme.background,
                tonalElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val pagerState =
                        rememberPagerState(pageCount = { 365 }, initialPage = 180)
                    LaunchedEffect(pagerState) {
                        // Collect from the a snapshotFlow reading the currentPage
                        snapshotFlow { pagerState.currentPage }.collect { page ->
                            // Do something with each page change, for example:
                            // viewModel.sendPageSelectedEvent(page)
                            //  Log.d("Page change", "Page changed to $page")

                        }
                    }
                    HorizontalPager(
                        state = pagerState,
                        //  pageCount = items.size,
                        modifier = Modifier.fillMaxWidth(),
                        // verticalAlignment = Alignment.
                    ) { page ->

                        Text(
                            modifier = Modifier.fillMaxWidth().wrapContentSize(),
                            text = "February 1",
                            textAlign = TextAlign.Center,

                            )

                    }
                }
            }
        }
    }
}
