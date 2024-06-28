package com.coroutines.thisdayinhistory.uimodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.coroutines.data.models.EventCategoryEnum
import com.coroutines.data.models.HistoricalEvent

class HistoryDataMap  constructor() : IHistoryDataMap {
    private val _historyEventsData = mutableStateListOf<HistoricalEvent>()
    private val _historySelectedData = mutableStateListOf<HistoricalEvent>()
    private val _historyBirthsData = mutableStateListOf<HistoricalEvent>()
    private val _historyDeathsData = mutableStateListOf<HistoricalEvent>()
    override val historyDataMap = mutableMapOf<EventCategoryEnum, SnapshotStateList<HistoricalEvent>>()
    override lateinit var currentKey: String
    init {
        historyDataMap.put(EventCategoryEnum.events, _historyEventsData)
        historyDataMap.put(EventCategoryEnum.selected, _historySelectedData)
        historyDataMap.put(EventCategoryEnum.deaths, _historyDeathsData)
        historyDataMap.put(EventCategoryEnum.births, _historyBirthsData)
    }
}