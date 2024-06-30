package com.coroutines.usecase



import com.coroutines.data.models.HistoricalEvent
import com.coroutines.models.synonyms.HistoryDay
import com.coroutines.models.synonyms.HistoryMonth
import kotlinx.coroutines.flow.Flow

interface IHistoryDataStandardUseCase {
    fun wikiFlow(
        month: HistoryMonth,
        day: HistoryDay,
        language: String,
        option: String,
    ): Flow<HistoricalEvent>
    fun wikiFlowList(
        month: HistoryMonth,
        day: HistoryDay,
        language: String,
        option: String,
    ): Flow<List<HistoricalEvent>>
}