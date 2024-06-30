package com.coroutines.api.wiki

import com.coroutines.models.synonyms.HistoryDay
import com.coroutines.models.synonyms.HistoryMonth
import com.coroutines.models.wiki.WikiEvent

interface WikiMediaApi {
    suspend fun getHistoricalEvents(
        historyMonth: HistoryMonth,
        historyDay: HistoryDay,
        language: String,
        option: String,
    ): WikiEvent
}