package com.coroutines.thisdayinhistory.uimodels

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.coroutines.data.models.EventCategoryEnum
import com.coroutines.data.models.HistoricalEvent

interface IHistoryDataMap {
    val historyDataMap: MutableMap<EventCategoryEnum, SnapshotStateList<HistoricalEvent>>
    var currentKey: String
}