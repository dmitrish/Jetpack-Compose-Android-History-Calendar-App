package com.coroutines.thisdayinhistory.ui.state

import com.coroutines.data.models.HistoricalEvent
import com.coroutines.thisdayinhistory.uimodels.CatsByLanguage
import com.coroutines.thisdayinhistory.uimodels.SelectedDate

data class HistoryViewModelState(
    val dataRequestState: DataRequestState,
    val selectedCategory: String,
    val previousCategory: String,
    val selectedItem: HistoricalEvent,
    val selectedDate: SelectedDate,
    val catsByLanguage: CatsByLanguage,
    val filter: String
)