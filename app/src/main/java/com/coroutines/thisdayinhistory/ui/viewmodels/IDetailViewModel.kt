package com.coroutines.thisdayinhistory.ui.viewmodels

import androidx.compose.runtime.MutableState
import com.coroutines.data.models.HistoricalEvent
import com.coroutines.models.wiki.Page

interface IDetailViewModel {
    val isSelectedEventPlaceholder: Boolean
    var selectedEvent: HistoricalEvent
    var historyImage: MutableState<String>
    val pages: List<Page>
}