package com.coroutines.thisdayinhistory.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.coroutines.data.models.HistoricalEvent
import com.coroutines.thisdayinhistory.ui.state.HistoryViewModelState
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime


interface IHistoryViewModel {

    val historyData: SnapshotStateList<HistoricalEvent>
    var isScrolled: MutableState<Boolean>
    var filterKey: String
    var selectedItem: HistoricalEvent
    val uiState: StateFlow<HistoryViewModelState>

    fun onDateChanged(localDateTime: LocalDateTime)
    fun updateDate(count: Int)
    fun onCategoryChanged(optionSelected: String)
    fun search(searchTerm: String)

}